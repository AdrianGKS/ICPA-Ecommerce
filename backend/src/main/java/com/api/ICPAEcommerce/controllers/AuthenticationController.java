package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.authentication.PasswordResetInputDTO;
import com.api.ICPAEcommerce.domain.user.authentication.PasswordUpdateWithTokenInputDTO;
import com.api.ICPAEcommerce.domain.user.authentication.TokenDTO;
import com.api.ICPAEcommerce.domain.user.authentication.UserAuthenticationDTO;
import com.api.ICPAEcommerce.infra.security.SecurityToken;
import com.api.ICPAEcommerce.repositories.UserRepository;
import com.api.ICPAEcommerce.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/** Rest Controller para requisições de autenticação e segurança
 * @author Adrian Gabriel K. dos Santos
 */
@RestController
@Slf4j
@RequestMapping("/api/v2/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private SecurityToken securityToken;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /** End-point para login na API
     *
     * @return 200 - token para usuário
     *         400 - erro no login
     */
    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDTO userAuthenticationDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password());
            var authentication = manager.authenticate(authenticationToken);
            var tokenJWT = securityToken.generateToken((User)authentication.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** End-point para esquecimento de senha
     *
     */
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody @Valid PasswordResetInputDTO input) {
        try {
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(input.email());

            optionalUser.ifPresent(user -> {
                var token = userService.generateToken( user);
                System.out.println(token);
                //token deve ser enviado pelo e-mail
            });
        } catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /** End-point para mudança de senha
     *
     */
    @PostMapping("/change-password")
    public void changePassword(PasswordUpdateWithTokenInputDTO input) {
        try {
            userService.changePassword(input.password(), input.token());

        } catch (Exception e) {
            log.error("Erro ao alterar a senha usando token: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
