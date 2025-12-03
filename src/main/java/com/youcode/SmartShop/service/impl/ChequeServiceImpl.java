package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.enums.PaymentStatus;
import com.youcode.SmartShop.exception.IncorrectInputException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ChequeMapper;
import com.youcode.SmartShop.repository.ChequeRepository;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.service.interfaces.IChequeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class ChequeServiceImpl implements IChequeService {

    private final ChequeRepository repository;
    private final ChequeMapper mapper;
    private  final CommandeRepository commandeRepository;

    @Override
    public ChequeResponseDto save(ChequeCreateRequestDto request)  {
        Cheque cheque = mapper.toEntity(request);

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
        cheque.setStatut(PaymentStatus.EN_ATTENTE);
        Cheque chequeSaved = repository.save(cheque);
        return mapper.toDTO(chequeSaved);
    }

    @Override
    public ChequeResponseDto getChequeById(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(()->new NotFoundException("cheque introuvable avec l'id"+id)));
    }

    @Override
    public Page<ChequeResponseDto> getAllCheque(Pageable pageable) {
        Page<Cheque> cheques  = repository.findAll(pageable);
        if(cheques.getTotalElements()<1)
            throw   new NotFoundException("Aucun cheque trouve");
        return cheques.map(mapper::toDTO);

    }

    @Override
    public String deleteChequeById(Long id) {
        if(!repository.existsById(id))
            throw new NotFoundException("cheque introuvable avec l'id"+id);
        repository.deleteById(id);
        return "le cheque supprime avec succes";
    }

}
