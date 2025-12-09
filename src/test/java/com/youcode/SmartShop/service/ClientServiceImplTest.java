package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.request.NiveauFideliteUpdateDto;
import com.youcode.SmartShop.dtos.request.UserCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.dtos.response.UserResponseDto;
import com.youcode.SmartShop.entity.Client;
import com.youcode.SmartShop.entity.User;
import com.youcode.SmartShop.enums.CustomerTier;
import com.youcode.SmartShop.enums.UserRole;
import com.youcode.SmartShop.exception.DuplicateClientException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ClientMapper;
import com.youcode.SmartShop.mapper.UserMapper;
import com.youcode.SmartShop.repository.ClientRepository;
import com.youcode.SmartShop.repository.UserRepository;
import com.youcode.SmartShop.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ClientServiceImpl service;

    private ClientCreateRequestDto requestDto;
    private ClientResponseDto responseDto;
    private Client client;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setRole(UserRole.CLIENT);
        user.setPassword("plainPassword");
        user.setUsername("hamza");

        client = new Client();
        client.setId(1L);
        client.setEmail("email@gmail.com");
        client.setNom("lakroune");
        client.setUser(user);
        client.setNiveauFidelite(CustomerTier.BASIC);

        requestDto = new ClientCreateRequestDto(
                "lakroune",
                "email@gmail.com",
                new UserCreateRequestDto("hamza", "plainPassword")
        );

        responseDto = new ClientResponseDto(
                1L,
                "lakroune",
                "email@gmail.com",
                CustomerTier.BASIC,
                new UserResponseDto("hamza",UserRole.CLIENT)
        );
    }

    @Test
    public void savesTest() {

        when(clientMapper.toEntity(requestDto)).thenReturn(client);
        when(userMapper.toEntity(requestDto.user())).thenReturn(user);
        when(clientMapper.toDTO(client)).thenReturn(responseDto);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientResponseDto clientSaved = service.save(requestDto);

        assertNotNull(clientSaved);
        verify(userRepository).save(any(User.class));
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void saveThrowDuplicateUsername() {
        when(userRepository.existsByUsername("hamza")).thenReturn(true);

        assertThrows(DuplicateClientException.class,
                () -> service.save(requestDto));

        verify(clientRepository, never()).save(any());

    }
    @Test
    void saveThrowDuplicateEmail() {
        when(userRepository.existsByUsername("hamza")).thenReturn(false);
        when(clientRepository.existsByEmail("email@gmail.com")).thenReturn(true);

        assertThrows(DuplicateClientException.class,
                () -> service.save(requestDto));

        verify(clientRepository, never()).save(any());
    }

    @Test
    public void getClientByIdTest(){
        Optional<Client> clientFind = Optional.of(client);
        when(clientRepository.findById(1L)).thenReturn(clientFind);
        when(clientMapper.toDTO(client)).thenReturn(responseDto);

        ClientResponseDto clientResponseDto = service.getClientById(1L);

        assertNotNull(clientResponseDto);
        assertEquals(responseDto.id(),clientResponseDto.id());
    }
    @Test
    public void getClientByIdThrowNotFound(){
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> service.getClientById(1L));

    }

    @Test
    public void updateNiveauFideliteTest(){
        NiveauFideliteUpdateDto niveaiRequest   =  new NiveauFideliteUpdateDto(CustomerTier.BASIC);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        client.setNiveauFidelite(CustomerTier.BASIC);

        when(clientMapper.toDTO(client)).thenReturn(responseDto);

        ClientResponseDto clientResponseDto = service.updateNiveauFidelite(1L,niveaiRequest);

        assertNotNull(clientResponseDto);
        assertEquals(clientResponseDto.id(),1l);
        verify(clientRepository).save(any(Client.class));

    }
    @Test
    public  void updateNiveauFideliteThrowNotFoundTest(){
        NiveauFideliteUpdateDto niveaiRequest   =  new NiveauFideliteUpdateDto(CustomerTier.BASIC);

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,()->service.updateNiveauFidelite(1L,niveaiRequest));
        verify(clientRepository, never()).save(any());
    }
    @Test
    public void  getAllTest(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> clientPage = new PageImpl<>(List.of(client), pageable, 1);
        when(clientRepository.findAll(pageable)).thenReturn(clientPage);
        when(clientMapper.toDTO(client)).thenReturn(responseDto);
        Page<ClientResponseDto> result = service.getAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(responseDto, result.getContent().get(0));
        verify(clientRepository).findAll(pageable);
        verify(clientMapper).toDTO(client);
    }
}
