package com.ap.homebanking;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){

		return(args ->{



			//create melba and another client
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("12345"));

			clientRepository.save(client1); //this saves client and generates PK (ID)
			Client client2 = new Client("Esteban", "Borro", "eborro@mindhub.com", passwordEncoder.encode("asdfg"));

			clientRepository.save(client2); //this saves client and generates PK (ID)

			Client client3 = new Client("admin", "admin", "admin@mindhub.com", passwordEncoder.encode("admin"));
			clientRepository.save(client3);//this saves the client named "admin" and generates a PK (ID)


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

			//transaction data
			Transaction transaction1 = new Transaction(TransactionType.Debit,-5000, "pizza order", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.Credit, 7500,"service charge", LocalDateTime.now());

			//assign transactions to accounts
			account1.addTransaction(transaction1);
			transactionRepository.save(transaction1);
			account2.addTransaction((transaction2));
			transactionRepository.save(transaction2);

			//create loans
			Loan loan1 = new Loan("Hipotecario", 500000, Set.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal",100000, Set.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 300000, Set.of(6,12,24,36));

			//save loans
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			//create clientloans for melba
			ClientLoan clientLoan1 = new ClientLoan(client1, loan1, 400000, Set.of(60));
			ClientLoan clientLoan2 = new ClientLoan(client1, loan2, 50000, Set.of(12));
			ClientLoan clientLoan3 = new ClientLoan(client2, loan2, 100000, Set.of(24));
			ClientLoan clientLoan4 = new ClientLoan(client2, loan3, 200000, Set.of(36));
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			//create, assign, and save cards
			Card card1= new Card(client1.getFirstName()+" "+client1.getLastName(),
					type.DEBIT,
					color.GOLD,
					61521105,
					877,
					LocalDate.now().plusYears(5),
					LocalDate.now(), client1);

			Card card2= new Card(
					client1.getFirstName()+" "+client1.getLastName(),
					type.CREDIT,
					color.TITANIUM,
					52412216,
					751,
					LocalDate.now().plusYears(5),
					LocalDate.now(), client1);

			Card card3= new Card(
					client2.getFirstName()+" "+client2.getLastName(),
					type.CREDIT,
					color.SILVER,
					70135988,
					635,
					LocalDate.now().plusYears(3),
					LocalDate.now(), client2);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		});

	}

}