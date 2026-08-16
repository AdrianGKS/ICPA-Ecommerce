package com.api.ICPAEcommerce.mappers;

import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.dto.product.DetailProductDTO;
import com.api.ICPAEcommerce.dto.product.ListProductDTO;
import com.api.ICPAEcommerce.dto.product.ProductDTO;
import com.api.ICPAEcommerce.dto.product.UpdateProductDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // 1. DTO para Entidade (Registro)
    // Ignoramos o ID (gerado pelo banco) e setamos os valores padrão do Soft Delete e Version
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "version", constant = "0L")
    Product toEntity(ProductDTO dto);

    // 2. Entidade para DTOs (Leitura)
    DetailProductDTO toDetailDTO(Product entity);

    ListProductDTO toListDTO(Product entity);

    // 3. Atualização (O MapStruct injeta os dados do DTO diretamente na Entidade existente)
    // O NullValuePropertyMappingStrategy.IGNORE garante que se o DTO mandar um nome "null", ele não apaga o nome do banco
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true) // Nunca atualizamos o ID
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "enumProductCategory", ignore = true)
    void updateEntityFromDTO(UpdateProductDTO dto, @MappingTarget Product entity);
}