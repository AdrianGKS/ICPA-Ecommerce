package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.dtos.RegisterDTO;
import com.api.ICPAEcommerce.domain.dtos.TokenDTO;
import com.api.ICPAEcommerce.domain.dtos.UserAuthenticationDTO;
import com.api.ICPAEcommerce.infra.security.SecurityToken;
import com.api.ICPAEcommerce.domain.models.User;
import com.api.ICPAEcommerce.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private SecurityToken securityToken;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/authentication")
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDTO userAuthenticationDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password());
            var authentication = manager.authenticate(authenticationToken);
            var tokenJWT = securityToken.generateToken((User)authentication.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterDTO dto) {
        if(this.userRepository.findByEmail(dto.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser =  new User(dto.email(), encryptedPassword, dto.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
