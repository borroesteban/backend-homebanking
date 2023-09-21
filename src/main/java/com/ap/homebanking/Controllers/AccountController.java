package com.ap.homebanking.Controllers;

import com.ap.homebanking.DTOS.AccountDTO;
import com.ap.homebanking.DTOS.ClientDTO;
import com.ap.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
