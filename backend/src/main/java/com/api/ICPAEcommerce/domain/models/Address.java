package com.api.ICPAEcommerce.domain.models;

import com.api.ICPAEcommerce.domain.dtos.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String number;
    private String city;
    private String neighborhood;
    private String state;
    private String cep;
    private String complement;

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.street();
        this.number = addressDTO.number();
        this.city = addressDTO.city();
        this.neighborhood = addressDTO.neighborhood();
        this.state = addressDTO.state();
        this.cep = addressDTO.cep();
        this.complement = addressDTO.complement();
    }
}
