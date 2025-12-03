package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.entity.Especes;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.enums.PaymentStatus;
import com.youcode.SmartShop.exception.IncorrectInputException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.EspecesMapper;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.repository.EspecesRepository;
import com.youcode.SmartShop.service.interfaces.IEspecesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class EspecesServiceImpl implements IEspecesService {

    private final EspecesMapper especesMapper;
    private final EspecesRepository especesRepository;
    private final CommandeRepository commandeRepository;

    @Override
    public EspecesResponseDto save(EspecesCreateRequestDto request) {
        Especes especes = especesMapper.toEntity(request);
        Commande commande = commandeRepository.findById(request.commande_id())
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));
        BigDecimal res = commande.getMontant_restant()
                .subtract(request.montant());
        if (res.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectInputException("Montant paye depasse le montant restant" +commande.getMontant_restant());
        }
        commande.setMontant_restant(res);
        if (res.compareTo(BigDecimal.ZERO) == 0) {
            commande.setStatut(OrderStatus.CANCELED);
        }
        commandeRepository.save(commande);
        especes.setStatut(PaymentStatus.EN_ATTENTE);
        Especes especesSaved = especesRepository.save(especes);
        return especesMapper.toDTO(especesSaved);
    }

    @Override
    public EspecesResponseDto getById(Long id) {
        return especesMapper.toDTO(especesRepository.findById(id).orElseThrow(()->new NotFoundException("especes introuvable avec l'id "+id)));
    }

    @Override
    public Page<EspecesResponseDto> getAll(Pageable pageable) {
        Page<Especes>  especesPage  = especesRepository.findAll(pageable);
        if(especesPage.isEmpty())
            throw new NotFoundException("aucun especes trouve");
        return especesPage.map(especesMapper::toDTO);
    }

    @Override
    public Page<EspecesResponseDto> getByCommandeId(Long id, Pageable pageable) {
        Page<Especes>  especesPage  = especesRepository.findByCommande_Id(id,pageable);
        if(especesPage.isEmpty())
            throw new NotFoundException("aucun especes trouve");
        return especesPage.map(especesMapper::toDTO);
    }
}
