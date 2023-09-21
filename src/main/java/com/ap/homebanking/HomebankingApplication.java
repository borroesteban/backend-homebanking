package com.ap.homebanking;

import com.ap.homebanking.Models.Account;
import com.ap.homebanking.Models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return(args ->{

			//create melba and another client
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client1); //this saves client and generates PK (ID)
			Client client2 = new Client("Esteban", "Borro", "eborro@mindhub.com");
			clientRepository.save(client2); //this saves client and generates PK (ID)

			//create two accounts
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN003", LocalDate.now(), 10000);
			Account account4 = new Account("VIN004", LocalDate.now(), 20000);

			//assign accounts to client
			client1.addAccount(account1);
			accountRepository.save(account1);
			client1.addAccount(account2);
			accountRepository.save(account2);
			client2.addAccount(account3);
			accountRepository.save(account3);
			client2.addAccount(account4);
			accountRepository.save(account4);
		});
	}
}