package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.user.authentication.PasswordTokenPublicDTO;
import com.api.ICPAEcommerce.domain.user.UserRegisterDTO;
import com.api.ICPAEcommerce.domain.user.UserUpdateDTO;
import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/** Classe de serviços para Usuário
 * @author Adrian Gabriel K. dos Santos
 *
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Implementação da classe UserDetails
     *
     * @return User
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    /** Métodos para salvar usuário no BD
     *
     * @return 200 - user
     */
    @Transactional
    public ResponseEntity saveUser(UserRegisterDTO userRegisterDTO) {

        if(this.userRepository.findByEmail(userRegisterDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.password());

        User newUser =  new User(userRegisterDTO.name(), userRegisterDTO.email(), encryptedPassword, userRegisterDTO.address(), userRegisterDTO.profile());

        this.userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    /** Métodos para atualizar infos de um usuário
     *
     * @return 200 - userUpdateDTO
     */
    @Transactional
    public ResponseEntity updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        var user = userRepository.getReferenceById(id);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    /** Métodos para deletar usuário
     *
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

/////////////////////////////////////////////////////////////////////////////
// MÉTODOS DE RECUPERAÇÃO DE SENHA

    @SneakyThrows
    public String generateToken(User user) {
        KeyBasedPersistenceTokenService tokenService = getInstanceFor(user);

        Token token = tokenService.allocateToken(user.getEmail());

        return token.getKey();
    }

    @SneakyThrows
    public void changePassword(String newPassword, String rawToken) {
        PasswordTokenPublicDTO publicDTO = readPublicData(rawToken);

        if (isExpited(publicDTO)) {
            throw new RuntimeException("Token expirado");        }

        User user = (User) userRepository.findByEmail(publicDTO.email());

        KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(user);
        tokenService.verifyToken(rawToken);

        user.setPassword(this.passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private KeyBasedPersistenceTokenService getInstanceFor(User user) throws Exception {
        KeyBasedPersistenceTokenService tokenService =  new KeyBasedPersistenceTokenService();
        tokenService.setServerSecret(user.getPassword());
        tokenService.setServerInteger(16);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        return tokenService;
    }

    private PasswordTokenPublicDTO readPublicData(String rawToken) {
        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];

        return new PasswordTokenPublicDTO(email, timestamp);
    }

    private boolean isExpited(PasswordTokenPublicDTO publicDTO) {
        Instant created = new Date(publicDTO.createAtTimestamp()).toInstant();
        Instant now = new Date().toInstant();

        return created.plus(Duration.ofMinutes(5)).isBefore(now);
    }

///////////////////////////////////////////////////////////////////////////////
}
