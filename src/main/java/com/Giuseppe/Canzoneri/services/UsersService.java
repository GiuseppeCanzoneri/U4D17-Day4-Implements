package com.Giuseppe.Canzoneri.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Giuseppe.Canzoneri.entities.User;
import com.Giuseppe.Canzoneri.exception.NotFoundException;
import com.Giuseppe.Canzoneri.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepo;

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));
	}
}