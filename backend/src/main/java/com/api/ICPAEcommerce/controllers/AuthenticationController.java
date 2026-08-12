package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.authentication.PasswordResetInputDTO;
import com.api.ICPAEcommerce.domain.user.authentication.PasswordUpdateWithTokenInputDTO;
import com.api.ICPAEcommerce.domain.user.authentication.TokenDTO;
import com.api.ICPAEcommerce.domain.user.authentication.UserAuthenticationDTO;
import com.api.ICPAEcommerce.infra.security.SecurityToken;
import com.api.ICPAEcommerce.repositories.UserRepository;
import com.api.ICPAEcommerce.services.UserService;
import com.api.ICPAEcommerce.services.ResetPasswordService;
import com.api.ICPAEcommerce.services.PasswordResetTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
@Tag(name = "Authetication")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final SecurityToken securityToken;

    private final UserService userService;

    private final UserRepository userRepository;

    private final ResetPasswordService resetPasswordService;

    private final PasswordResetTokenService passwordResetTokenService;

    /** End-point para login na API
     *
     * @return 200 - token para usuário
     *         400 - erro no login
     */
    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDTO userAuthenticationDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = securityToken.generateToken((User)authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    /** End-point para esquecimento de senha
     * Gera um token persistido de reset e envia por e-mail
     */
    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestBody @Valid PasswordResetInputDTO input) {
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(input.email());
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Gerar token persistido usando PasswordResetTokenService
            String resetToken = passwordResetTokenService.createPasswordResetToken(user);
            
            try {
                // Enviar e-mail com link contendo o token
                resetPasswordService.sendEmailResetPassword(
                    user.getEmail(),
                    "Password Reset",
                    user.getName(),
                    resetToken
                );
                log.info("E-mail de reset de senha enviado para: {}", user.getEmail());
                return ResponseEntity.ok("E-mail de reset de senha enviado com sucesso");
            } catch (Exception e) {
                log.error("Erro ao enviar e-mail de reset de senha: ", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar e-mail");
            }
        }
        
        // Por segurança, não informar se o e-mail existe ou não
        return ResponseEntity.ok("Se o e-mail existir em nosso sistema, você receberá um link de reset");
    }

    /** End-point para mudança de senha com token
     * Valida o token, altera a senha e marca token como usado
     */
    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid PasswordUpdateWithTokenInputDTO input) {
        try {
            // Validar token (lança exceção se inválido/expirado/usado)
            var resetToken = passwordResetTokenService.validateToken(input.token());
            
            // Alterar senha
            userService.changePasswordWithToken(input.password(), resetToken);
            
            // Marcar token como consumido
            passwordResetTokenService.consumeToken(resetToken);
            
            log.info("Senha alterada com sucesso para usuário: {}", resetToken.getUser().getEmail());
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (IllegalArgumentException e) {
            log.warn("Tentativa de reset com token inválido/expirado: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido ou expirado");
        } catch (Exception e) {
            log.error("Erro ao alterar senha: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao alterar senha");
        }
    }
}


