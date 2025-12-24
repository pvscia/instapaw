package com.example.instapaws.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.instapaws.model.User;


public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{
    Optional<User> findByUsername(String username);

}
