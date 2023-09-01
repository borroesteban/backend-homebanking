package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

/*    Implementa el método en el controlador de cuentas que cree una nueva cuenta, la
asocie al cliente con la sesión iniciada, guarde la cuenta a través del el repositorio*/

    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping(path = "/api/clients/current/accounts", method = RequestMethod.POST)

    //logica comprobar cantidad de cuentas
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use",
                    HttpStatus.FORBIDDEN);
        }

        //logica de salvar la cuenta en el usuario
        clientRepository.save(new Client(firstName, lastName,
                email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
