package com.api.ICPAEcommerce.dto.user;

import com.api.ICPAEcommerce.dto.address.AddressDTO;
import com.api.ICPAEcommerce.domain.user.EnumUserProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password,

        @Valid
        AddressDTO address,

        @NotNull(message = "O perfil do usuário é obrigatório")
        EnumUserProfile profile
) {}
