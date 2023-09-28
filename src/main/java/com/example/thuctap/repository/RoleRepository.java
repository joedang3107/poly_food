package com.example.thuctap.repository;

import com.example.thuctap.models.ERole;
import com.example.thuctap.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional <Role> findByRoleName(ERole roleName);

}
