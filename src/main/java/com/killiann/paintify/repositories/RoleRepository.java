package com.killiann.paintify.repositories;

import com.killiann.paintify.models.ERole;
import com.killiann.paintify.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}