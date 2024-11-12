package com.example.userauthservice.Repositories;

import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
}
