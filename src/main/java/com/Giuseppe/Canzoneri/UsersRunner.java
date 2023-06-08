package com.Giuseppe.Canzoneri;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Giuseppe.Canzoneri.entities.User;
import com.Giuseppe.Canzoneri.repositories.UsersRepository;
import com.github.javafaker.Faker;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UsersRepository usersRepo;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));
		List<User> usersDB = usersRepo.findAll();

		if (usersDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {

					String name = faker.name().firstName();
					String username = faker.name().username();
					String email = faker.internet().emailAddress();
					String password = faker.internet().password();
					User user = new User(name, username, email, password);
					usersRepo.save(user);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}