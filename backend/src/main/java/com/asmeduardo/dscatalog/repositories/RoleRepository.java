package com.asmeduardo.dscatalog.repositories;

import com.asmeduardo.dscatalog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
