package com.ap.homebanking.Controllers;
import com.ap.homebanking.DTOS.AccountDTO;
import com.ap.homebanking.DTOS.ClientDTO;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        //return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
        }
/*    @Autowired
    AccountRepository accountRepository;
    @RequestMapping("/Accounts")
    public Set<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }*/

    /*
    @Autowired
    AccountRepository accountRepository;
    public Set<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }*/
}
