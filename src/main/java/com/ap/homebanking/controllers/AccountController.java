package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    private AccountDTO getId(@PathVariable Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAcc(Authentication authentication) {
        Client authUser;
        authUser=clientRepository.findByEmail(authentication.getName());
        if(authUser.getAccounts().size()>3){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            Random random = new Random();
            int randomNumber = random.nextInt(99999999) + 1;
            String randomNumberAsString = Integer.toString(randomNumber);
            Account account=new Account("VIN" + randomNumberAsString, LocalDate.now(), 0);
            accountRepository.save(account);
            authUser.addAccount(account);
            clientRepository.save(authUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
}





