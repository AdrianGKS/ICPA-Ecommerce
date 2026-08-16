package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.authentication.PasswordResetToken;
import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.UserMapper;
import com.api.ICPAEcommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

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
        List<User> users = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.getFirst().getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar usuário por ID")
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void testFindByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.findById(999L)
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void testDeleteUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.deleteUser(999L)
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("Deve carregar usuário pelo email")
    void testLoadUserByUsername() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        var result = userService.loadUserByUsername(" test@example.com ");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Deve lançar exceção ao carregar usuário inexistente pelo email")
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("notfound@example.com")
        );

        assertEquals("Usuário não encontrado em nosso sistema.", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    @DisplayName("Deve alterar senha com token válido")
    void testChangePasswordWithToken() {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);

        when(passwordEncoder.encode("new_password")).thenReturn("encoded_new_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePasswordWithToken("new_password", resetToken);

        assertEquals("encoded_new_password", user.getPassword());
        verify(passwordEncoder, times(1)).encode("new_password");
        verify(userRepository, times(1)).save(user);
    }
}
