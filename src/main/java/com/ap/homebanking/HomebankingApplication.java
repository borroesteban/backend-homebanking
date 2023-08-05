package com.ap.homebanking;

import com.ap.homebanking.Models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository repository){
		return(args) ->{
			//create client melba
			repository.save(new Client(1,"Melba", "Morel", "melba@mindhub.com"));
			repository.save(new Client(2,"Esteban", "Borro", "eborro@mindhub.com"));
		};
	}
}