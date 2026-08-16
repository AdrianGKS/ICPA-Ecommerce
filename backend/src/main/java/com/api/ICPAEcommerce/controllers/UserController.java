package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.mapper.UserMapper;
import com.api.ICPAEcommerce.dto.user.UserRegisterDTO;
import com.api.ICPAEcommerce.dto.user.UserResponseDTO;
import com.api.ICPAEcommerce.dto.user.UserUpdateDTO;
import com.api.ICPAEcommerce.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/** Rest Controller para requisições de usuário
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Requests")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Rota responsável pelo cadastro de usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pessoa cadastrada com sucesso",
                    content = { @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegisterDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário já registrado",
                    content = { @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
   @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO, UriComponentsBuilder uriComponentsBuilder) {
        var savedUser = userService.registerUser(userRegisterDTO);
        var response = userMapper.toResponse(savedUser);
        var uri = uriComponentsBuilder.path("/api/v1/users/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Rota responsável pela listagem de usuários")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    @GetMapping("/list-users")
    @ResponseBody
    public ResponseEntity<List<UserResponseDTO>> listUsers() {
        var users = userService.listUsers();
        var response = users.stream().map(userMapper::toResponse).collect(Collectors.toList());
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Rota responsável pela listagem de usuários por ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário não encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    @GetMapping("/list-user/{userId}")
    @ResponseBody
    public ResponseEntity<UserResponseDTO> listUserById(@PathVariable Long userId) {
        var user = userService.findById(userId);
        var response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Rota responsável pela atualização de usuários")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário não encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        var updatedUser = userService.updateUser(userId, userUpdateDTO);
        var response = userMapper.toResponse(updatedUser);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Rota responsável por deletar usuários")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário excluído",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID inválido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    }
            )
    })
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
