package com.ap.homebanking.DTOS;

import com.ap.homebanking.Models.Account;
import com.ap.homebanking.Models.Client;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(toSet());
    }

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {

        return creationDate;
    }
    public double getBalance() {

        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}