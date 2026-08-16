package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.authentication.PasswordResetToken;
import com.api.ICPAEcommerce.domain.user.User;
import com.api.ICPAEcommerce.dto.user.UserRegisterDTO;
import com.api.ICPAEcommerce.dto.user.UserUpdateDTO;
import com.api.ICPAEcommerce.domain.user.mapper.UserMapper;
import com.api.ICPAEcommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Classe de serviços para Usuário
 * @author Adrian Gabriel K. dos Santos
 *
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /** Implementação da classe UserDetails
     *
     * @return User
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username.trim());
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado em nosso sistema.");
        }
        return user;    }

    /** Métodos para salvar usuário no BD
     *
     * @return 200 - user
     */
    @Transactional
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        if(this.userRepository.findByEmail(userRegisterDTO.email()) != null) {
            throw new IllegalArgumentException("Usuário já registrado");
        }

        User user =  userMapper.toEntity(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.password()));

        return this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    /** Métodos para atualizar infos de um usuário
     *
     * @return 200 - userUpdateDTO
     */
    @Transactional
    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
        userMapper.updateEntityFromDto(userUpdateDTO, existingUser);
        return userRepository.save(existingUser);
    }

    /** Métodos para deletar usuário
     *
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
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

