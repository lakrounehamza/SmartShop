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
    @Mock private ClientRepository clientRepository;
    @Mock private IOrderItemService orderItemService;
    @Mock private ProductRepository productRepository;
    @Mock private OrderItemMapper orderItemMapper;

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

    @Test
    void getByIdTest() {
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(commandeMapper.toDTO(commande)).thenReturn(
                new CommandeResponseDto(1L, 1L, new ArrayList<>(), new ArrayList<>(), LocalDate.now(),
                        BigDecimal.valueOf(200), 0, 0, BigDecimal.valueOf(200), null, OrderStatus.PENDING, BigDecimal.ZERO)
        );

        CommandeResponseDto result = commandeService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void getByIdThrowNotFound() {
        when(commandeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> commandeService.getById(1L));
    }


    @Test
    void getByClientIdTest() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Commande> page = new PageImpl<>(List.of(commande));

        when(commandeRepository.findByClient_Id(1L, pageable)).thenReturn(page);
        when(commandeMapper.toDTO(commande)).thenReturn(
                new CommandeResponseDto(1L, 1L, new ArrayList<>(), new ArrayList<>(), LocalDate.now(),
                        BigDecimal.valueOf(200), 0, 0, BigDecimal.valueOf(200), null, OrderStatus.PENDING, BigDecimal.ZERO)
        );

        Page<CommandeResponseDto> result = commandeService.getByClientId(1L, pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getByClientIdThrowNotFound() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(commandeRepository.findByClient_Id(1L, pageable)).thenReturn(Page.empty());

        assertThrows(NotFoundException.class,
                () -> commandeService.getByClientId(1L, pageable));
    }

    // -------------------------------------------------------------
    //                      TEST GET ALL
    // -------------------------------------------------------------

    @Test
    void getAll_ShouldReturnPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Commande> page = new PageImpl<>(List.of(commande));

        when(commandeRepository.findAll(pageable)).thenReturn(page);
        when(commandeMapper.toDTO(commande)).thenReturn(
                new CommandeResponseDto(1L, 1L, new ArrayList<>(), new ArrayList<>(), LocalDate.now(),
                        BigDecimal.valueOf(200), 0, 0, BigDecimal.valueOf(200), null, OrderStatus.PENDING, BigDecimal.ZERO)
        );

        Page<CommandeResponseDto> result = commandeService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAll_ShouldThrowNotFound() {
        when(commandeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        assertThrows(NotFoundException.class,
                () -> commandeService.getAll(PageRequest.of(0, 10)));
    }


    @Test
    void getClientStatisticTest() {

        when(clientRepository.existsById(1L)).thenReturn(true);
        when(commandeRepository.findCountByClinet_id(1L)).thenReturn(5);
        when(commandeRepository.findCumuleByClient_Id(1L)).thenReturn(Optional.of(BigDecimal.valueOf(5000)));
        when(commandeRepository.findFirstDateByClient_id(1L)).thenReturn(Optional.of(LocalDate.now().minusDays(10)));
        when(commandeRepository.findLastDateByClient_id(1L)).thenReturn(Optional.of(LocalDate.now()));

        ClinetStatisticResponseDto stats = commandeService.getClientStatistic(1L);

        assertEquals(5, stats.total());
    }

    @Test
    void getClientStatisticThrowNotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class,
                () -> commandeService.getClientStatistic(1L));
    }



}
