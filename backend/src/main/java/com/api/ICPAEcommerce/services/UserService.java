package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.dtos.UserDTO;
import com.api.ICPAEcommerce.domain.models.User;
import com.api.ICPAEcommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Transactional
    public ResponseEntity saveUser(UserDTO userDTO) {

        if(this.userRepository.findByEmail(userDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

        User newUser =  new User(userDTO.name(), userDTO.email(), encryptedPassword, userDTO.address(), userDTO.profile());

        this.userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @Transactional
    public ResponseEntity updateUser(Long id, UserDTO userDTO) {
        var user = userRepository.getReferenceById(id);

        if (userDTO.name() != null) {
            user.setName(userDTO.name());
        }

        if (userDTO.email() != null) {
            var findUserByEmail = userRepository.findByEmailIgnoreCase(userDTO.email());

            if (findUserByEmail.isPresent()) {
                return ResponseEntity.ok("Email já cadastrado");
            }

            user.setEmail(userDTO.email());
        }

        if (userDTO.password() != null) {
            var newPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(newPassword);
        }

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//////////////////////////////////////////////////////////////////////////////////////////
/*
    @Transactional
    public String generateLinkToUpdatePassword(UserRecoverPasswordDTO userRecoverPasswordDTO) {
        var user = userRepository.findByEmailIgnoreCase(userRecoverPasswordDTO.email());

        return generateToken(user.get());
    }



    public ResponseEntity<Object> updatePassword(String token, String password) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);

        if(tokenOptional.isEmpty()) {
            return ResponseEntity.ok().body("Token inválido!");
        }

        if(tokenOptional.get().getConfirmed() != null) {
            return ResponseEntity.ok().body("Token já foi utilizado, solicite novamente alteração de password");
        }

        if(tokenOptional.get().getExpires().isBefore(OffsetDateTime.now())) {
            return ResponseEntity.ok().body("Token expirado, solicite novamente alteração de password");
        }

        Optional<User> userOptional = userRepository.findById(tokenOptional.get().getUser().getId());
        userOptional.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(userOptional.get());

        //Salvar dados do token
        tokenOptional.get().setConfirmed(OffsetDateTime.now());
        tokenRepository.save(tokenOptional.get());

        return ResponseEntity.ok().body("Senha atualizada com sucesso!");
    }
    */

}
