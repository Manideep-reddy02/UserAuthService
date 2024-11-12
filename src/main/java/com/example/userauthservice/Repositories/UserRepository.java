package com.example.userauthservice.Repositories;

import com.example.userauthservice.models.User;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);

    Optional<User>findByEmail(String email);

}
