package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.UserRegisterDTO;
import com.api.ICPAEcommerce.domain.user.address.AddressDTO;
import com.api.ICPAEcommerce.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encoded_password");
        user.setName("Test User");
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void testListUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar usuário por ID")
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar empty quando usuário não existe")
    void testFindByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(999L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve alterar senha com token válido")
    void testChangePasswordWithToken() {
        com.api.ICPAEcommerce.domain.user.authentication.PasswordResetToken resetToken = 
            new com.api.ICPAEcommerce.domain.user.authentication.PasswordResetToken();
        resetToken.setUser(user);

        when(passwordEncoder.encode("new_password")).thenReturn("encoded_new_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePasswordWithToken("new_password", resetToken);

        verify(passwordEncoder, times(1)).encode("new_password");
        verify(userRepository, times(1)).save(any(User.class));
    }
}



