package com.Giuseppe.Canzoneri;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Giuseppe.Canzoneri.entities.Citta;
import com.Giuseppe.Canzoneri.entities.Edificio;
import com.Giuseppe.Canzoneri.repositories.CittaRepository;
import com.Giuseppe.Canzoneri.repositories.EdificiRepository;
import com.github.javafaker.Faker;

@Component
public class EdificiRunner implements CommandLineRunner {
	@Autowired
	EdificiRepository edificiRepo;
	@Autowired
	CittaRepository cittaRepo;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));
		List<Edificio> edificiDB = edificiRepo.findAll();
		List<Citta> cittaDB = cittaRepo.findAll();

		if (edificiDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {

					String name = faker.twinPeaks().character();
					String indirizzo = faker.address().fullAddress();
					int randomIndex = new Random().nextInt(cittaDB.size());
					Citta randomCitta = cittaDB.get(randomIndex);
					Edificio edificio = new Edificio(name, indirizzo, randomCitta);
					edificiRepo.save(edificio);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

}