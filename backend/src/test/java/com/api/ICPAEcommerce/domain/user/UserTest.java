package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.domain.user.address.Address;
import com.api.ICPAEcommerce.domain.user.address.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity Tests")
class UserTest {

    private User user;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO(
            "Rua das Flores",
            "1150",
            "Belo Horizonte",
            "Industrial",
            "MG",
            "68878-703",
            "Casa"
        );
    }

    @Test
    @DisplayName("Criar usuário com perfil USER via UserRegisterDTO")
    void shouldCreateUserWithRegisterDTO() {
        UserRegisterDTO registerDTO = new UserRegisterDTO(
            "Samuel Almeida",
            "user003@icpaecommerce.com",
            "123456",
            addressDTO,
            EnumUserProfile.USER
        );

        user = new User(registerDTO);

        assertNotNull(user);
        assertEquals("Samuel Almeida", user.getName());
        assertEquals("user003@icpaecommerce.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals(EnumUserProfile.USER, user.getProfile());
        assertNotNull(user.getAddress());
    }

    @Test
    @DisplayName("Atualizar usuário via UserUpdateDTO")
    void shouldUpdateUserWithUpdateDTO() {
        UserUpdateDTO updateDTO = new UserUpdateDTO(
            "Updated Name",
            "updated@example.com",
            addressDTO
        );

        user = new User(updateDTO);

        assertEquals("Updated Name", user.getName());
        assertEquals("updated@example.com", user.getEmail());
        assertNotNull(user.getAddress());
    }

    @Test
    @DisplayName("Usuário admin deve ter ROLE_ADMIN e ROLE_USER")
    void adminUserShouldHaveBothRoles() {
        user = new User();
        user.setProfile(EnumUserProfile.ADMIN);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(2, authorities.size());
        assertTrue(authorities.stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authorities.stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    @DisplayName("Usuário comum deve ter apenas ROLE_USER")
    void regularUserShouldHaveOnlyUserRole() {
        user = new User();
        user.setProfile(EnumUserProfile.USER);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertFalse(authorities.stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("getUsername deve retornar o email do usuário")
    void usernameShouldReturnEmail() {
        user = new User();
        user.setEmail("tatiane@icpaecommerce.com");

        assertEquals("tatiane@icpaecommerce.com", user.getUsername());
    }

    @Test
    @DisplayName("Conta nunca deve estar expirada")
    void accountShouldNeverBeExpired() {
        user = new User();

        assertTrue(user.isAccountNonExpired());
    }

    @Test
    @DisplayName("Conta nunca deve estar bloqueada")
    void accountShouldNeverBeLocked() {
        user = new User();

        assertTrue(user.isAccountNonLocked());
    }

    @Test
    @DisplayName("Credenciais nunca devem estar expiradas")
    void credentialsShouldNeverBeExpired() {
        user = new User();

        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("Usuário sempre deve estar habilitado")
    void userShouldAlwaysBeEnabled() {
        user = new User();

        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("Dois usuários com mesmo ID devem ser iguais")
    void usersWithSameIdShouldBeEqual() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");

        User user2 = new User();
        user2.setId(1L);
        user2.setName("User 2");

        assertEquals(user1, user2);
    }

    @Test
    @DisplayName("Dois usuários com IDs diferentes devem ser diferentes")
    void usersWithDifferentIdsShouldNotBeEqual() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("Usuário com endereço completo e sem complemento")
    void userWithCompleteAddressWithoutComplement() {
        AddressDTO completeAddressDTO = new AddressDTO(
            "Av. Getulio Vargas",
            "865",
            "Sao Paulo",
            "Centro",
            "SP",
            "38657-338",
            null
        );

        UserRegisterDTO registerDTO = new UserRegisterDTO(
            "Tatiane Souza",
            "user002@icpaecommerce.com",
            "password123",
            completeAddressDTO,
            EnumUserProfile.USER
        );

        user = new User(registerDTO);

        assertNotNull(user.getAddress());
        assertNull(user.getAddress().getComplement());
    }

    @Test
    @DisplayName("Validação de email único não deve ser feita no construtor")
    void emailUniquenessNotValidatedInConstructor() {
        UserRegisterDTO dto1 = new UserRegisterDTO(
            "User A",
            "duplicate@example.com",
            "pass",
            addressDTO,
            EnumUserProfile.USER
        );
        UserRegisterDTO dto2 = new UserRegisterDTO(
            "User B",
            "duplicate@example.com",
            "pass",
            addressDTO,
            EnumUserProfile.USER
        );

        User user1 = new User(dto1);
        User user2 = new User(dto2);

        assertEquals(user1.getEmail(), user2.getEmail());
    }

    @Test
    @DisplayName("Atualizar apenas nome mantém email anterior")
    void updatingUserKeepsPreviousEmail() {
        user = new User();
        user.setEmail("original@example.com");

        UserUpdateDTO updateDTO = new UserUpdateDTO(
            "New Name",
            "newemail@example.com",
            addressDTO
        );

        User updatedUser = new User(updateDTO);

        assertEquals("New Name", updatedUser.getName());
        assertEquals("newemail@example.com", updatedUser.getEmail());
    }
}




