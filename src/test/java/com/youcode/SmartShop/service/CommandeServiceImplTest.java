package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.*;
import com.youcode.SmartShop.dtos.response.*;
import com.youcode.SmartShop.entity.*;
import com.youcode.SmartShop.enums.*;
import com.youcode.SmartShop.exception.*;
import com.youcode.SmartShop.mapper.CommandeMapper;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.*;
import com.youcode.SmartShop.service.impl.CommandeServiceImpl;
import com.youcode.SmartShop.service.interfaces.IOrderItemService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceImplTest {

    @Mock private CommandeMapper commandeMapper;
    @Mock private CommandeRepository commandeRepository;

    @InjectMocks
    private CommandeServiceImpl commandeService;

    private Commande commande;
    private Client client;
    private Product product;

    @BeforeEach
    void setup() {

        client = new Client();
        client.setId(1L);
        client.setNiveauFidelite(CustomerTier.SILVER);

        product = new Product(1L, "Produit A", BigDecimal.valueOf(100), 50);

        commande = new Commande();
        commande.setId(1L);
        commande.setClient(client);
        commande.setSousTotal(BigDecimal.valueOf(200));
        commande.setMontant_restant(BigDecimal.ZERO);
        commande.setStatut(OrderStatus.PENDING);
        commande.setAticles(new ArrayList<>());
        commande.setPaiement(new ArrayList<>());
    }

    @Test
    void savedTest() {
        CommandeCreateRequestDto request = new CommandeCreateRequestDto(1L, null);

        when(commandeMapper.toEntity(request)).thenReturn(commande);
        when(commandeRepository.save(commande)).thenReturn(commande);
        when(commandeMapper.toDTO(commande)).thenReturn(
                new CommandeResponseDto(1L, 1L, new ArrayList<>(), new ArrayList<>(), LocalDate.now(),
                        BigDecimal.valueOf(200), 0, 0, BigDecimal.valueOf(200), null, OrderStatus.PENDING, BigDecimal.ZERO)
        );

        CommandeResponseDto result = commandeService.save(request);

        assertNotNull(result);
        verify(commandeRepository).save(commande);
    }




}
