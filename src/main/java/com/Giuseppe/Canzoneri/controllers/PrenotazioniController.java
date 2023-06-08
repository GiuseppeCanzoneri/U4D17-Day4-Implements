package com.Giuseppe.Canzoneri.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Giuseppe.Canzoneri.entities.Prenotazione;
import com.Giuseppe.Canzoneri.payload.PrenotazioneRegistrationPayload;
import com.Giuseppe.Canzoneri.services.PrenotazioniService;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
	@Autowired
	private PrenotazioniService prenotazioniService;

	@GetMapping("")
	public Page<Prenotazione> getPrenotazione(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return prenotazioniService.find(page, size, sortBy);
	}

	@GetMapping("/info")
	public String info() {
		return "Inserisci una lingua scegliendo tra italiano e inglese";
	}

	@GetMapping("/info/{lingua}")
	public String infoIta(@PathVariable String lingua) {
		if (lingua.equals("ita")) {
			return "Benvenut nel sistema di prenotazioni delle postazioni di lavoro; puoi scegliere in base alla città , all'edificio e al tipo di postazione richiesta.Ricorda che ogni prenotazione effettuata è valida per 24 ore.";
		} else if (lingua.equals("eng")) {
			return "Welcome to the workstation reservation system; you can choose according to the city, the building and the type of seat requested. Remember that every reservation made is valid for 24 hours.";
		} else {
			return "Inserisci una lingua scegliendo tra italiano e inglese";
		}
	}

	@GetMapping("/{prenotazioneId}")
	public Prenotazione getPrenotazione(@PathVariable UUID prenotazioneId) throws Exception {
		return prenotazioniService.findById(prenotazioneId);
	}

	@PostMapping("")
	public Prenotazione createPrenotazione(@RequestBody PrenotazioneRegistrationPayload body) {

		return prenotazioniService.create(body);
	}

	@PutMapping("/{prenotazioneId}")
	public Prenotazione updatePrenotazione(@PathVariable UUID prenotazioneId,
			@RequestBody PrenotazioneRegistrationPayload body) {
		return prenotazioniService.findByIdAndUpdate(prenotazioneId, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePrenotazione(@PathVariable UUID id) {
		prenotazioniService.findByIdAndDelete(id);
	}

}