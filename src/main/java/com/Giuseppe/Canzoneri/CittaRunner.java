package com.Giuseppe.Canzoneri;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Giuseppe.Canzoneri.entities.Citta;
import com.Giuseppe.Canzoneri.repositories.CittaRepository;
import com.github.javafaker.Faker;

@Component
public class CittaRunner implements CommandLineRunner {
	@Autowired
	CittaRepository cittaRepo;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));
		List<Citta> cittaDB = cittaRepo.findAll();

		if (cittaDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {

					String name = faker.address().cityName();

					Citta citta = new Citta(name);
					cittaRepo.save(citta);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}