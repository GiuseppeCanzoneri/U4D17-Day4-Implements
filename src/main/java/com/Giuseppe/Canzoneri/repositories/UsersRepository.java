package com.Giuseppe.Canzoneri.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Giuseppe.Canzoneri.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

}