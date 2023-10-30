package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.domain.user.address.AddressDTO;
import com.api.ICPAEcommerce.domain.user.address.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private String password;
    private EnumUserProfile profile;

    @Embedded
    private @Valid Address address;


    public User(UserRegisterDTO userRegisterDTO) {
        this.name = userRegisterDTO.name();
        this.email = userRegisterDTO.email();
        this.password = userRegisterDTO.password();
        this.address = new Address(userRegisterDTO.address());
        this.profile = userRegisterDTO.profile();
    }

    public User(String name, String email, String encryptedPassword, AddressDTO address, EnumUserProfile profile) {
        this.name = name;
        this.email = email;
        this.password = encryptedPassword;
        this.address = new Address(address);
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.profile == EnumUserProfile.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
