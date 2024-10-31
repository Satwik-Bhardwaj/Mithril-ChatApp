package com.userservice.Repository;


import com.userservice.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUsername(String name);


}
