package com.Giuseppe.Canzoneri.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Giuseppe.Canzoneri.entities.Postazione;
import com.Giuseppe.Canzoneri.services.PostazioniService;

@RestController
@RequestMapping("/postazioni")
public class PostazioniController {
	@Autowired
	private PostazioniService postazioniService;

	@GetMapping("")
	public Page<Postazione> getPostazioni(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return postazioniService.find(page, size, sortBy);
	}

	@GetMapping("/{postazioneId}")
	public Postazione getPostazione(@PathVariable UUID postazioneId) throws Exception {
		return postazioniService.findById(postazioneId);
	}

}