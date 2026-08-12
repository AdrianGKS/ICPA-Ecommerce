package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.UserRegisterDTO;
import com.api.ICPAEcommerce.domain.user.UserUpdateDTO;
import com.api.ICPAEcommerce.repositories.UserRepository;
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

/** Rest Controller para requisições de usuário
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Requests")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

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
    public ResponseEntity registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO, UriComponentsBuilder uriComponentsBuilder) {
       var user = userService.registerUser(userRegisterDTO);
       var uri = uriComponentsBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
       return ResponseEntity.created(uri).body(user);
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
    public ResponseEntity listUsers() {
        var users = userService.listUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.ok("Não há usuários cadastrados");
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
    public ResponseEntity listUserById(@PathVariable Long userId) {
        var optional = userService.findById(userId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.badRequest().body("Usuário não encontrado");
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
    public ResponseEntity updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userId, userUpdateDTO);
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
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        var optional = userService.findById(userId);
        if (optional.isPresent()) {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Usuário excluído com sucesso!");
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

}
