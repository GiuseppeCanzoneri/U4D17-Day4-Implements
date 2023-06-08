package com.Giuseppe.Canzoneri.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Giuseppe.Canzoneri.entities.Postazione;
import com.Giuseppe.Canzoneri.entities.Prenotazione;
import com.Giuseppe.Canzoneri.entities.User;
import com.Giuseppe.Canzoneri.exception.BadRequestException;
import com.Giuseppe.Canzoneri.exception.NotFoundException;
import com.Giuseppe.Canzoneri.payload.PrenotazioneRegistrationPayload;
import com.Giuseppe.Canzoneri.repositories.PrenotazioniRepository;

@Service
public class PrenotazioniService {
	@Autowired
	private PrenotazioniRepository prenotazioniRepo;
	@Autowired
	private PostazioniService postazioneService;
	@Autowired
	private UsersService userService;

	public Prenotazione create(PrenotazioneRegistrationPayload p) {
		Postazione postazione = postazioneService.findById(p.getPostazioneId());
		prenotazioniRepo.findByPostazioneAndDataPrenotata(postazione, p.getDataPrenotata()).ifPresent(prenotazione -> {
			throw new BadRequestException("Postazione " + p.getPostazioneId() + " already in use!");
		});
		LocalDate dueGiorniDopo = LocalDate.now().plusDays(2);
		User user = userService.findById(p.getUserId());
		if (p.getDataPrenotata().isAfter(dueGiorniDopo)) {
			Prenotazione newPrenotazione = new Prenotazione(user, postazione, p.getDataPrenotata(), LocalDate.now());
			return prenotazioniRepo.save(newPrenotazione);
		} else {
			throw new BadRequestException("Devono esserci almeno due giorni prima della data di presentazione.");
		}

	}

	public Page<Prenotazione> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return prenotazioniRepo.findAll(pageable);
	}

	public Prenotazione findById(UUID id) throws NotFoundException {
		return prenotazioniRepo.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione non trovata!"));
	}

	public Prenotazione findByIdAndUpdate(UUID id, PrenotazioneRegistrationPayload p) throws NotFoundException {
		Prenotazione found = this.findById(id);
		Postazione postazione = postazioneService.findById(p.getPostazioneId());
		User user = userService.findById(p.getUserId());
		LocalDate dueGiorniDopo = LocalDate.now().plusDays(2);
		if (p.getDataPrenotata().isAfter(dueGiorniDopo)) {
			found.setId(id);
			found.setPostazione(postazione);
			found.setUser(user);
			found.setDataPrenotata(p.getDataPrenotata());
			return prenotazioniRepo.save(found);
		} else {
			throw new BadRequestException("Devono esserci almeno due giorni prima della data di presentazione.");
		}

	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Prenotazione found = this.findById(id);
		prenotazioniRepo.delete(found);
	}

}