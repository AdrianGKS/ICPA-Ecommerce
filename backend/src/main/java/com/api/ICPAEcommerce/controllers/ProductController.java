package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/** Rest controller das requisições sobre Produtos
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Requests")
@Validated
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Rota responsável pelo cadastro de produtos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto cadastrado com sucesso",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailProductDTO.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Produto já cadastrado!",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            )
    })
    @PostMapping("/register-product")
    public ResponseEntity registerProduct(@RequestBody @Valid ProductDTO productDTO,
                                          UriComponentsBuilder uriBuilder) {
        return productService.register(productDTO, uriBuilder);
    }

    @Operation(summary = "Rota responsável pela listagem de produtos.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de Produtos cadastrados.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )}
            )
    })
    @GetMapping("/list-products")
    public ResponseEntity<Page<ListProductDTO>> listProducts(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return productService.listProducts(pageable);
    }

    @Operation(summary = "Rota responsável pela listagem de produtos pelo nome.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de Produtos cadastrados pelo nome.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )}
            )
    })
    @GetMapping("/list-products/name/{name}")
    public ResponseEntity<Page<ListProductDTO>> listProductsByName(@PathVariable String name,
                                                                   @PageableDefault(sort = {"name"}) Pageable pageable) {

        return productService.listProductsByName(name, pageable);
    }

    @Operation(summary = "Rota responsável pela listagem de produtos pela categoria.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de Produtos cadastrados pela categoria.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )}
            )
    })
    @GetMapping("/list-products/category/{category}")
    public ResponseEntity<Page<ListProductDTO>> listProductsByCategory(@PathVariable EnumProductCategory category,
                                                                       @PageableDefault Pageable pageable) {

        return productService.listProductsByCategory(category, pageable);
    }

    @Operation(summary = "Rota responsável pela listagem de produtos pelo código.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de Produtos cadastrados pelo código.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )}
            )
    })
    @GetMapping("/list-product/code/{code}")
    public ResponseEntity listProductByCode(@PathVariable String code) {

        return productService.listProductByCode(code);
    }

    @Operation(summary = "Rota responsável pela cálculo total de estoque.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Valor total de estoque.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TotalStockDTO.class)
                    )}
            )
    })
    @GetMapping("/total-stock-value")
    public ResponseEntity<?> totalStockValue() {

        return productService.totalStockValue();
    }

    @Operation(summary = "Rota responsável pela atualização de produtos.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailProductDTO.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EntityNotFoundException.class)
                    )
                    }
            )
    })
    @PutMapping("/update-product/{id}")
    public ResponseEntity updateProduct(@RequestParam Long id, @RequestBody @Valid UpdateProductDTO productDTO) {

        return productService.updateProduct(id, productDTO);
    }

    @Operation(summary = "Rota responsável pela deleção de produtos.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto deletado.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            )
    })
    @DeleteMapping("/delete-product/{id}")
        public ResponseEntity deleteProduct(@PathVariable Long id) {

        return productService.deleteProduct(id);
    }
}
