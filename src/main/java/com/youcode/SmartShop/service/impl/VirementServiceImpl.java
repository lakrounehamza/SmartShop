package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.entity.Virement;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.exception.IncorrectInputException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.VirementMapper;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.repository.VirementRepository;
import com.youcode.SmartShop.service.interfaces.IVirementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class VirementServiceImpl implements IVirementService {
    private final VirementRepository  repository;
    private final VirementMapper  mapper;
    private final CommandeRepository commandeRepository;
    @Override
    public VirementResponseDto save(VirementCreateRequestDto request) {
        Virement virement  = mapper.toEntity(request);
        Virement virementSaved = repository.save(virement);
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
        return mapper.toDTO(virementSaved);
    }

    @Override
    public VirementResponseDto getById(Long id) {
        return  mapper.toDTO(repository.findById(id).orElseThrow(()->new NotFoundException("virement introuvable avec l'id"+id)));
    }

    @Override
    public Page<VirementResponseDto> getByCommabdeId(Long id, Pageable pageable) {
        Page<Virement>  virements  = repository.findByCommande_Id(id,pageable);
        if(virements.isEmpty())
            throw  new NotFoundException("aucun virement trouve");
        return virements.map(mapper::toDTO);    }

    @Override
    public Page<VirementResponseDto> getAll(Pageable pageable) {
        Page<Virement>  virements  = repository.findAll(pageable);
        if(virements.isEmpty())
            throw  new NotFoundException("aucun virement trouve");
        return virements.map(mapper::toDTO);
    }
}
