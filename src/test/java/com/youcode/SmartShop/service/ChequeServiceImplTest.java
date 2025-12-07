package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.enums.PaymentStatus;
import com.youcode.SmartShop.enums.PaymentType;
import com.youcode.SmartShop.exception.IncorrectInputException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ChequeMapper;
import com.youcode.SmartShop.repository.ChequeRepository;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.service.impl.ChequeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChequeServiceImplTest {

    @Mock
    private ChequeRepository chequeRepository;
    @Mock
    private ChequeMapper chequeMapper;
    @Mock
    private CommandeRepository commandeRepository;
    @InjectMocks
    private ChequeServiceImpl chequeService;

    private Cheque cheque;
    private Commande commande;
    private ChequeCreateRequestDto requestDto;
    private ChequeResponseDto responseDto;

    @BeforeEach
    void setup() {
        commande = new Commande();
        commande.setId(1L);
        commande.setMontant_restant(BigDecimal.valueOf(100));
        commande.setStatut(OrderStatus.PENDING);

        cheque = new Cheque();
        cheque.setId(1L);

        requestDto = new ChequeCreateRequestDto(
                "PAY123456",
                BigDecimal.valueOf(50),
                PaymentType.CHEQUE,
                LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(2),
                "CHQ12345",
                "BANQUE BMCE",
                LocalDate.now().plusDays(3),
                1L
        );

        responseDto = new ChequeResponseDto(
                1L,
                "PAY123456",
                BigDecimal.valueOf(50),
                PaymentType.CHEQUE,
                requestDto.date_paiement(),
                requestDto.date_encaissement(),
                "CHQ12345",
                "BANQUE POP",
                requestDto.echance(),
                PaymentStatus.EN_ATTENTE
        );
    }

    @Test
    void saveCheque_Success() {
        when(chequeMapper.toEntity(requestDto)).thenReturn(cheque);
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(chequeRepository.save(cheque)).thenReturn(cheque);
        when(chequeMapper.toDTO(cheque)).thenReturn(responseDto);

        ChequeResponseDto result = chequeService.save(requestDto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(50), commande.getMontant_restant());
        assertEquals(PaymentStatus.EN_ATTENTE, cheque.getStatut());
        assertEquals("PAY123456", result.numero_paiement());

        verify(commandeRepository).save(commande);
        verify(chequeRepository).save(cheque);
    }

    @Test
    void saveCheque_IncorrectMontantTest() {
        ChequeCreateRequestDto badRequest = new ChequeCreateRequestDto(
                "PAY999",
                BigDecimal.valueOf(200),
                PaymentType.CHEQUE,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                "CHQ88888",
                "CIH",
                LocalDate.now().plusDays(1),
                1L
        );

        when(chequeMapper.toEntity(badRequest)).thenReturn(cheque);
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        assertThrows(IncorrectInputException.class, () -> chequeService.save(badRequest));
    }

    @Test
    void saveCheque_CommandeNotFoundTest() {
        when(commandeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> chequeService.save(requestDto));
    }

    @Test
    void getChequeById_Success() {
        when(chequeRepository.findById(1L)).thenReturn(Optional.of(cheque));
        when(chequeMapper.toDTO(cheque)).thenReturn(responseDto);

        ChequeResponseDto result = chequeService.getChequeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(chequeRepository).findById(1L);
    }

    @Test
    void getChequeById_NotFound() {
        when(chequeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> chequeService.getChequeById(1L));
    }

    @Test
    void getAllCheque_Success() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Cheque> page = new PageImpl<>(List.of(cheque));

        when(chequeRepository.findAll(pageable)).thenReturn(page);
        when(chequeMapper.toDTO(cheque)).thenReturn(responseDto);

        Page<ChequeResponseDto> result = chequeService.getAllCheque(pageable);

        assertEquals(1, result.getTotalElements());
        verify(chequeRepository).findAll(pageable);
    }

    @Test
    void getAllCheque_Empty() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Cheque> emptyPage = new PageImpl<>(List.of());

        when(chequeRepository.findAll(pageable)).thenReturn(emptyPage);

        assertThrows(NotFoundException.class, () -> chequeService.getAllCheque(pageable));
    }

    @Test
    void deleteChequeById_Success() {
        when(chequeRepository.existsById(1L)).thenReturn(true);

        String result = chequeService.deleteChequeById(1L);

        assertEquals("le cheque supprime avec succes", result);
        verify(chequeRepository).deleteById(1L);
    }

    @Test
    void deleteChequeById_NotFound() {
        when(chequeRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> chequeService.deleteChequeById(1L));
    }
}