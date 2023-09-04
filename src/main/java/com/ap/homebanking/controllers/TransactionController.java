package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.dtos.TransactionDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.LoanRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.ap.homebanking.models.type.DEBIT;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> startTransaction(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam double amount,
            @RequestParam String description,
            Authentication authentication) {

        Client authUser = clientRepository.findByEmail(authentication.getName());
        Account originAccount = accountRepository.findByNumber(fromAccountNumber);
        Account targetAccount = accountRepository.findByNumber(toAccountNumber);

        if (amount == 0.0) {
            return new ResponseEntity<>("ERROR. Missing data: amount", HttpStatus.FORBIDDEN);
        } else if (fromAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: origin account number", HttpStatus.FORBIDDEN);
        } else if (toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: target account number", HttpStatus.FORBIDDEN);
        } else if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("ERROR. Origin Account and Target Account are the same", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(fromAccountNumber) == null) {
            return new ResponseEntity<>("ERROR. Origin Account does not exist", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("ERROR. Target Account does not exist", HttpStatus.FORBIDDEN);
        } else if (authUser.getAccounts().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))){
            return new ResponseEntity<>("ERROR. Origin Account does not belong to user", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(fromAccountNumber).getBalance()<amount){
            return new ResponseEntity<>("ERROR. Origin Account insufficient founds", HttpStatus.FORBIDDEN);
        } else {

            Transaction transaction1 = new Transaction(TransactionType.Debit, - amount, fromAccountNumber + description, LocalDateTime.now());
            Transaction transaction2 = new Transaction(TransactionType.Credit, + amount, toAccountNumber + description, LocalDateTime.now());

            originAccount.addTransaction(transaction1);
            originAccount.setBalance(originAccount.getBalance()-amount);

            targetAccount.addTransaction(transaction2);
            targetAccount.setBalance(targetAccount.getBalance()+amount);


            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            accountRepository.save(originAccount);
            accountRepository.save(targetAccount);
            clientRepository.save(authUser);
            return new ResponseEntity<>("Transaction Success", HttpStatus.CREATED);
        }
    }
}