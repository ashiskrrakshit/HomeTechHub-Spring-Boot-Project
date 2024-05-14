package com.project.hometechhub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

}
