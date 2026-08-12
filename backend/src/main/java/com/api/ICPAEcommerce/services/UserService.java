package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.domain.user.UserRegisterDTO;
import com.api.ICPAEcommerce.domain.user.UserUpdateDTO;
import com.api.ICPAEcommerce.domain.user.authentication.PasswordResetToken;
import com.api.ICPAEcommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Classe de serviços para Usuário
 * @author Adrian Gabriel K. dos Santos
 *
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        if(this.userRepository.findByEmail(userRegisterDTO.email()) != null) {
            throw new IllegalArgumentException("Usuário já registrado");
        }

        User user =  new User(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.password()));

        this.userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /** Métodos para atualizar infos de um usuário
     *
     * @return 200 - userUpdateDTO
     */
    @Transactional
    public ResponseEntity updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        if (userRepository.existsById(id)) {
            var user = new User(userUpdateDTO);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    /** Métodos para deletar usuário
     *
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    /**
     * Altera a senha de um usuário após validação de token de reset
     * @param newPassword nova senha em plaintext
     * @param resetToken token de reset validado pelo PasswordResetTokenService
     */
    @Transactional
    public void changePasswordWithToken(String newPassword, PasswordResetToken resetToken) {
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

