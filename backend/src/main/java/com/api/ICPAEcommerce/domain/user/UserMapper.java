package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.dto.user.UserRegisterDTO;
import com.api.ICPAEcommerce.dto.user.UserResponseDTO;
import com.api.ICPAEcommerce.dto.user.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserRegisterDTO dto);

    // Esse método atualiza o usuário que já existe com os dados do DTO, ignorando campos nulos!
    void updateEntityFromDto(UserUpdateDTO dto, @MappingTarget User entity);

    UserResponseDTO toResponse(User entity);

    List<UserResponseDTO> toResponseList(List<User> entities);
}
