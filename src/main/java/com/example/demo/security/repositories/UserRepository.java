package com.example.demo.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.security.entities.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {
}
