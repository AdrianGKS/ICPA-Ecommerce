package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.dto.address.AddressDTO;
import com.api.ICPAEcommerce.dto.user.UserRegisterDTO;
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
    @DisplayName("Criar usuário com perfil USER")
    void shouldCreateUserWithUserProfile() {
        user = new User();
        user.setName("Samuel Almeida");
        user.setEmail("user003@icpaecommerce.com");
        user.setPassword("123456");
        user.setProfile(EnumUserProfile.USER);

        Address address = new Address();
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setCity(addressDTO.city());
        address.setNeighborhood(addressDTO.neighborhood());
        address.setState(addressDTO.state());
        address.setCep(addressDTO.cep());
        address.setComplement(addressDTO.complement());
        user.setAddress(address);

        assertNotNull(user);
        assertEquals("Samuel Almeida", user.getName());
        assertEquals("user003@icpaecommerce.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals(EnumUserProfile.USER, user.getProfile());
        assertNotNull(user.getAddress());
    }

    @Test
    @DisplayName("Atualizar usuário manualmente")
    void shouldUpdateUserFields() {
        user = new User();
        user.setName("Updated Name");
        user.setEmail("updated@example.com");

        Address address = new Address();
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setCity(addressDTO.city());
        address.setNeighborhood(addressDTO.neighborhood());
        address.setState(addressDTO.state());
        address.setCep(addressDTO.cep());
        address.setComplement(addressDTO.complement());
        user.setAddress(address);

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

        Address address = new Address();
        address.setStreet(completeAddressDTO.street());
        address.setNumber(completeAddressDTO.number());
        address.setCity(completeAddressDTO.city());
        address.setNeighborhood(completeAddressDTO.neighborhood());
        address.setState(completeAddressDTO.state());
        address.setCep(completeAddressDTO.cep());
        address.setComplement(completeAddressDTO.complement());

        user = new User();
        user.setName("Tatiane Souza");
        user.setEmail("user002@icpaecommerce.com");
        user.setPassword("password123");
        user.setProfile(EnumUserProfile.USER);
        user.setAddress(address);

        assertNotNull(user.getAddress());
        assertNull(user.getAddress().getComplement());
    }

    @Test
    @DisplayName("Validação de email único não deve ser feita na entidade")
    void emailUniquenessNotValidatedInEntity() {
        User user1 = new User();
        user1.setEmail("duplicate@example.com");

        User user2 = new User();
        user2.setEmail("duplicate@example.com");

        assertEquals(user1.getEmail(), user2.getEmail());
    }

    @Test
    @DisplayName("Atualizar nome e email do usuário")
    void updatingUserFieldsShouldChangeValues() {
        user = new User();
        user.setEmail("original@example.com");

        user.setName("New Name");
        user.setEmail("newemail@example.com");

        assertEquals("New Name", user.getName());
        assertEquals("newemail@example.com", user.getEmail());
    }
}




