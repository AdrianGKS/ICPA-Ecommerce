package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.authentication.PasswordResetToken;
import com.api.ICPAEcommerce.domain.user.EnumUserProfile;
import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.UserMapper;
import com.api.ICPAEcommerce.dto.user.UserRegisterDTO;
import com.api.ICPAEcommerce.dto.user.UserUpdateDTO;
import com.api.ICPAEcommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository repository;
    @Mock PasswordEncoder encoder;
    @Mock UserMapper mapper;
    @InjectMocks UserService service;

    @Test
    void loadUserTrimsUsernameAndRejectsUnknownUser() {
        User user = new User(); user.setEmail("user@example.com");
        when(repository.findByEmail("user@example.com")).thenReturn(user);
        assertSame(user, service.loadUserByUsername(" user@example.com "));
        when(repository.findByEmail("missing@example.com")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("missing@example.com"));
    }

    @Test
    void registerEncodesPasswordAndRejectsDuplicateEmail() {
        UserRegisterDTO dto = new UserRegisterDTO("User", "user@example.com", "plainpass", null, EnumUserProfile.USER);
        User user = new User();
        when(repository.findByEmail(dto.email())).thenReturn(null);
        when(mapper.toEntity(dto)).thenReturn(user);
        when(encoder.encode("plainpass")).thenReturn("encoded");
        when(repository.save(user)).thenReturn(user);
        assertSame(user, service.registerUser(dto));
        assertEquals("encoded", user.getPassword());
        verify(repository).save(user);
        when(repository.findByEmail(dto.email())).thenReturn(user);
        assertThrows(IllegalArgumentException.class, () -> service.registerUser(dto));
    }

    @Test
    void listFindUpdateAndDeleteUsers() {
        User user = new User(); user.setId(1L);
        when(repository.findAll()).thenReturn(List.of(user));
        assertEquals(List.of(user), service.listUsers());
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        assertSame(user, service.findById(1L));
        UserUpdateDTO dto = new UserUpdateDTO("Updated", null, null);
        when(repository.save(user)).thenReturn(user);
        assertSame(user, service.updateUser(1L, dto));
        verify(mapper).updateEntityFromDto(dto, user);
        service.deleteUser(1L);
        verify(repository).delete(user);
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void changePasswordWithTokenSavesEncodedPassword() {
        User user = new User(); PasswordResetToken token = new PasswordResetToken(); token.setUser(user);
        when(encoder.encode("new-password")).thenReturn("encoded");
        service.changePasswordWithToken("new-password", token);
        assertEquals("encoded", user.getPassword());
        verify(repository).save(user);
    }
}
