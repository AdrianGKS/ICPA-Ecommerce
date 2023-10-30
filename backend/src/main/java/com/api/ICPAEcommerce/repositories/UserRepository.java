package com.api.ICPAEcommerce.repositories;

import com.api.ICPAEcommerce.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);


    Optional<User> findByEmailIgnoreCase(String email);
}
