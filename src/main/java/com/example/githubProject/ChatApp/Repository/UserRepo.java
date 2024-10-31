package com.example.githubProject.ChatApp.Repository;


import com.example.githubProject.ChatApp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String name);


}
