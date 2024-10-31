package com.example.githubProject.ChatApp.Repository;

import com.example.githubProject.ChatApp.Entity.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepo extends JpaRepository<BaseEntity,Integer> {

}
