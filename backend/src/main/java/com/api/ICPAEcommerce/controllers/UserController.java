package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.models.User;
import com.api.ICPAEcommerce.domain.dtos.UserDTO;
import com.api.ICPAEcommerce.domain.dtos.UserTokenDTO;
import com.api.ICPAEcommerce.infra.security.SecurityToken;
import com.api.ICPAEcommerce.repositories.UserRepository;
import com.api.ICPAEcommerce.services.RegisterEmailService;
import com.api.ICPAEcommerce.services.ResetPasswordService;
import com.api.ICPAEcommerce.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private SecurityToken securityToken;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterEmailService registerEmailService;

    @Autowired
    private ResetPasswordService resetPasswordService;

   /* @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody @Valid UserDTO userDTO) {
        var findUserByEmail = userRepository.findByEmailIgnoreCase(userDTO.email());

        if (!findUserByEmail.isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        var newUser = userService.saveUser(userDTO);

        var authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = securityToken.generateToken((User) authentication.getPrincipal());

/// envio de token por email

        UserTokenDTO userTokenDTO = new UserTokenDTO(newUser, tokenJWT);

        String receiver = userDTO.email();
        String subject = "Bem-vindo ao ICPA-Ecommerce";
        String name = userDTO.name();
        String token = tokenJWT;

        try {
            registerEmailService.sendEmail(receiver, subject, name, token);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(userTokenDTO);
    }*/

    @GetMapping("/list-users")
    @ResponseBody
    public ResponseEntity listUsers() {
        var users = userRepository.findAll();
        if(!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }

        return ResponseEntity.ok("Não há usuários cadastrados");
    }

    @GetMapping("/list-user/{id}")
    @ResponseBody
    public ResponseEntity listUserById(@PathVariable Long id) {
        var user = userRepository.getReferenceById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        var user = userService.updateUser(id, userDTO);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("Usuário excluído com sucesso!");
    }

   /* @PostMapping("/recover-password")
    public ResponseEntity recoverPassword(@RequestBody @Valid UserRecoverPasswordDTO userRecoverPasswordDTO) {
        var user = userRepository.findByEmailIgnoreCase(userRecoverPasswordDTO.email());

        if(user.isEmpty()) {
            return ResponseEntity.ok().body("Usuário não existe para o e-mail informado!");
        }

        var tokenUser = userService.generateLinkToUpdatePassword(userRecoverPasswordDTO);

        String link = "recover-password/" + tokenUser;
        String receiver = userRecoverPasswordDTO.email();
        String subject = "Solicitação de Alteração de Senha";
        String name = user.get().getName();

        try {
            resetPasswordService.sendEmailResetPassword(receiver, subject, name, link);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body("E-mail enviado com sucesso!");
    }


    @PostMapping("/update-password/{token}")
    public ResponseEntity updatePassword(@PathVariable String token, @RequestBody @Valid UserChangePasswordDTO userChangePasswordDTO) {
        var updatePassword = userService.updatePassword(token, userChangePasswordDTO.password());

        return ResponseEntity.ok().body(updatePassword);
    }
*/
}
