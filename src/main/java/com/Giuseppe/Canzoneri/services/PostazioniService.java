package com.Giuseppe.Canzoneri.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Giuseppe.Canzoneri.entities.Postazione;
import com.Giuseppe.Canzoneri.exception.NotFoundException;
import com.Giuseppe.Canzoneri.repositories.PostazioniRepository;

@Service
public class PostazioniService {
	@Autowired
	private PostazioniRepository postazioniRepo;

	public Page<Postazione> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return postazioniRepo.findAll(pageable);
	}

	public Postazione findById(UUID id) throws NotFoundException {
		return postazioniRepo.findById(id).orElseThrow(() -> new NotFoundException("Postazione non trovata!"));
	}
}