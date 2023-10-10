package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.models.dtos.TokenDto;
import com.api.ICPAEcommerce.domain.models.dtos.UserAuthenticationDTO;
import com.api.ICPAEcommerce.infra.security.SecurityToken;
import com.api.ICPAEcommerce.domain.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private SecurityToken securityToken;

    @PostMapping("/authentication")
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDTO userAuthenticationDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthenticationDTO.email(), userAuthenticationDTO.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = securityToken.generateToken((User)authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(tokenJWT));
    }
}
