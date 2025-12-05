package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.request.UserCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.entity.Client;
import com.youcode.SmartShop.entity.User;
import com.youcode.SmartShop.enums.CustomerTier;
import com.youcode.SmartShop.enums.UserRole;
import com.youcode.SmartShop.exception.DuplicateClientException;
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
                new UserCreateRequestDto("hamza", "plainPassword", UserRole.CLIENT)
        );

        responseDto = new ClientResponseDto(
                1L,
                "lakroune",
                "email@gmail.com",
                CustomerTier.BASIC,
                user
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
}
