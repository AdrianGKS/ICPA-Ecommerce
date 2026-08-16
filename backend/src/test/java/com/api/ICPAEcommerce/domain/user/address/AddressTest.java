package com.api.ICPAEcommerce.domain.user.address;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.dto.address.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Address Entity Tests")
class AddressTest {

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO(
            "Av. Paulista",
            "1282",
            "Sao Paulo",
            "Santa Luzia",
            "SP",
            "30585-345",
            "Casa"
        );
    }

    @Test
    @DisplayName("Criar endereço com sucesso via AddressDTO")
    void shouldCreateAddressFromDTO() {
        address = new Address(addressDTO);

        assertNotNull(address);
        assertEquals("Av. Paulista", address.getStreet());
        assertEquals("1282", address.getNumber());
        assertEquals("Sao Paulo", address.getCity());
        assertEquals("Santa Luzia", address.getNeighborhood());
        assertEquals("SP", address.getState());
        assertEquals("30585-345", address.getCep());
        assertEquals("Casa", address.getComplement());
    }

    @Test
    @DisplayName("Endereço sem complemento")
    void addressWithoutComplement() {
        AddressDTO addressDTONoComplement = new AddressDTO(
            "Rua das Flores",
            "1150",
            "Belo Horizonte",
            "Industrial",
            "MG",
            "68878-703",
            null
        );

        address = new Address(addressDTONoComplement);

        assertNull(address.getComplement());
        assertEquals("Rua das Flores", address.getStreet());
    }

    @Test
    @DisplayName("Atualizar rua do endereço")
    void updateAddressStreet() {
        address = new Address(addressDTO);
        address.setStreet("Avenida Brasil");

        assertEquals("Avenida Brasil", address.getStreet());
    }

    @Test
    @DisplayName("Atualizar número do endereço")
    void updateAddressNumber() {
        address = new Address(addressDTO);
        address.setNumber("500");

        assertEquals("500", address.getNumber());
    }

    @Test
    @DisplayName("Atualizar cidade do endereço")
    void updateAddressCity() {
        address = new Address(addressDTO);
        address.setCity("Rio de Janeiro");

        assertEquals("Rio de Janeiro", address.getCity());
    }

    @Test
    @DisplayName("Atualizar bairro do endereço")
    void updateAddressNeighborhood() {
        address = new Address(addressDTO);
        address.setNeighborhood("Centro");

        assertEquals("Centro", address.getNeighborhood());
    }

    @Test
    @DisplayName("Atualizar estado do endereço")
    void updateAddressState() {
        address = new Address(addressDTO);
        address.setState("RJ");

        assertEquals("RJ", address.getState());
    }

    @Test
    @DisplayName("Atualizar CEP do endereço")
    void updateAddressCEP() {
        address = new Address(addressDTO);
        address.setCep("20000-000");

        assertEquals("20000-000", address.getCep());
    }

    @Test
    @DisplayName("Atualizar complemento do endereço")
    void updateAddressComplement() {
        address = new Address(addressDTO);
        address.setComplement("Apto 201");

        assertEquals("Apto 201", address.getComplement());
    }

    @Test
    @DisplayName("Remover complemento do endereço")
    void removeAddressComplement() {
        address = new Address(addressDTO);
        address.setComplement(null);

        assertNull(address.getComplement());
    }

    @Test
    @DisplayName("Criar endereço vazio via construtor padrão")
    void createEmptyAddressViaDefaultConstructor() {
        address = new Address();

        assertNull(address.getStreet());
        assertNull(address.getNumber());
        assertNull(address.getCity());
        assertNull(address.getNeighborhood());
        assertNull(address.getState());
        assertNull(address.getCep());
        assertNull(address.getComplement());
    }

    @Test
    @DisplayName("Criar endereço completo via construtor")
    void createCompleteAddressViaConstructor() {
        address = new Address(
            "Rua do Comercio",
            "1710",
            "Belo Horizonte",
            "Cidade Alta",
            "MG",
            "69871-664",
            "Sala 5"
        );

        assertEquals("Rua do Comercio", address.getStreet());
        assertEquals("1710", address.getNumber());
        assertEquals("Belo Horizonte", address.getCity());
        assertEquals("Cidade Alta", address.getNeighborhood());
        assertEquals("MG", address.getState());
        assertEquals("69871-664", address.getCep());
        assertEquals("Sala 5", address.getComplement());
    }

    @Test
    @DisplayName("Endereço com número muito longo")
    void addressWithVeryLongNumber() {
        AddressDTO dto = new AddressDTO(
            "Rua Test",
            "12345678901234567890",
            "City",
            "Neighborhood",
            "ST",
            "12345-678",
            null
        );

        address = new Address(dto);

        assertEquals("12345678901234567890", address.getNumber());
    }

    @Test
    @DisplayName("Endereço com complemento muito longo")
    void addressWithVeryLongComplement() {
        String longComplement = "A".repeat(200);
        AddressDTO dto = new AddressDTO(
            "Rua Test",
            "123",
            "City",
            "Neighborhood",
            "ST",
            "12345-678",
            longComplement
        );

        address = new Address(dto);

        assertEquals(longComplement, address.getComplement());
    }

    @Test
    @DisplayName("Endereço com todos os campos vazios não deve gerar exceção")
    void addressWithAllEmptyFieldsNotException() {
        AddressDTO dto = new AddressDTO(
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        );

        address = new Address(dto);

        assertEquals("", address.getStreet());
        assertEquals("", address.getNumber());
    }

    @Test
    @DisplayName("CEP em formato diferente")
    void cepInDifferentFormat() {
        AddressDTO dto = new AddressDTO(
            "Rua Test",
            "100",
            "City",
            "Neighborhood",
            "ST",
            "12345678",
            null
        );

        address = new Address(dto);

        assertEquals("12345678", address.getCep());
    }

    @Test
    @DisplayName("Atualizar múltiplos campos do endereço")
    void updateMultipleAddressFields() {
        address = new Address(addressDTO);

        address.setStreet("Nova Rua");
        address.setNumber("999");
        address.setCity("Nova Cidade");
        address.setNeighborhood("Novo Bairro");

        assertEquals("Nova Rua", address.getStreet());
        assertEquals("999", address.getNumber());
        assertEquals("Nova Cidade", address.getCity());
        assertEquals("Novo Bairro", address.getNeighborhood());
    }

    @Test
    @DisplayName("Copiar dados de endereço via DTO")
    void copyAddressDataViaDTO() {
        AddressDTO newDTO = new AddressDTO(
            "Rua Nova",
            "777",
            "Brasilia",
            "Asa Sul",
            "DF",
            "70000-000",
            "Fundos"
        );

        address = new Address(newDTO);

        assertEquals("Rua Nova", address.getStreet());
        assertEquals("777", address.getNumber());
        assertEquals("Brasilia", address.getCity());
        assertEquals("Asa Sul", address.getNeighborhood());
        assertEquals("DF", address.getState());
        assertEquals("70000-000", address.getCep());
        assertEquals("Fundos", address.getComplement());
    }

    @Test
    @DisplayName("Endereço com estado em diferentes formatos")
    void addressWithStateInDifferentFormats() {
        AddressDTO dto1 = new AddressDTO("Rua", "1", "City", "Neighborhood", "SP", "12345-678", null);
        AddressDTO dto2 = new AddressDTO("Rua", "1", "City", "Neighborhood", "MG", "12345-678", null);
        AddressDTO dto3 = new AddressDTO("Rua", "1", "City", "Neighborhood", "RJ", "12345-678", null);

        address = new Address(dto1);
        assertEquals("SP", address.getState());

        address = new Address(dto2);
        assertEquals("MG", address.getState());

        address = new Address(dto3);
        assertEquals("RJ", address.getState());
    }
}

