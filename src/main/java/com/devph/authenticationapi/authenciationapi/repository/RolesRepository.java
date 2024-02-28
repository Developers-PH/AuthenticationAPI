package com.devph.authenticationapi.authenciationapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devph.authenticationapi.authenciationapi.models.entity.EnumRole;
import com.devph.authenticationapi.authenciationapi.models.entity.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(EnumRole name);

}
