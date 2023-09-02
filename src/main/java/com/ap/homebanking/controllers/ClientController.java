package com.ap.homebanking.controllers;
import com.ap.homebanking.configurations.WebAuthentication;
import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        }
    @RequestMapping("/clients/{id}")
    private ClientDTO getId(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
        }


    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use",
                    HttpStatus.FORBIDDEN);
        }
        clientRepository.save(new Client(firstName, lastName,
                email, passwordEncoder.encode(password)));

        //call method to create account defined below
        createAcc(email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //identify new client by email
    //add acc to it
    @Autowired
    AccountRepository accountRepository;
    public void createAcc(String email){
    Client authUser;
    authUser=clientRepository.findByEmail(email);
    Random random = new Random();
    int randomNumber = random.nextInt(99999999) + 1;
    String randomNumberAsString = Integer.toString(randomNumber);
    Account account=new Account("VIN" + randomNumberAsString, LocalDate.now(), 0);
        accountRepository.save(account);
        authUser.addAccount(account);
       }








    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}
