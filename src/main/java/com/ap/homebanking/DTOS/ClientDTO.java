package com.ap.homebanking.DTOS;

import com.ap.homebanking.Models.Account;
import com.ap.homebanking.Models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    //Set<Account> accounts=new HashSet<>();
    private Set<AccountDTO> accounts;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }


    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}



