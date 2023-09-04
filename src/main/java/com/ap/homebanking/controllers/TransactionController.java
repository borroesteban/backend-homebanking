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
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam String originAccountNumber,
            @RequestParam String targetAccountNumber,
            Authentication authentication) {
        Client authUser = clientRepository.findByEmail(authentication.getName());
        Account originAccount = accountRepository.findByNumber(originAccountNumber);
        Account targetAccount = accountRepository.findByNumber(targetAccountNumber);
        Transaction transaction1 = new Transaction(TransactionType.Debit, - amount, originAccountNumber + description, LocalDateTime.now());
        Transaction transaction2 = new Transaction(TransactionType.Credit, + amount, targetAccountNumber + description, LocalDateTime.now());
        if (amount == 0.0) {
            return new ResponseEntity<>("ERROR. Missing data: amount", HttpStatus.FORBIDDEN);
        } else if (originAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: origin account number", HttpStatus.FORBIDDEN);
        } else if (targetAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: target account number", HttpStatus.FORBIDDEN);
        } else if (originAccountNumber.equals(targetAccountNumber)) {
            return new ResponseEntity<>("ERROR. Origin Account and Target Account are the same", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(originAccountNumber) == null) {
            return new ResponseEntity<>("ERROR. Origin Account does not exist", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(targetAccountNumber) == null) {
            return new ResponseEntity<>("ERROR. Target Account does not exist", HttpStatus.FORBIDDEN);
        } else if (authUser.getAccounts().stream().noneMatch(account -> account.getNumber().equals(originAccountNumber))){
            return new ResponseEntity<>("ERROR. Origin Account does not belong to user", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(originAccountNumber).getBalance()<amount){
            return new ResponseEntity<>("ERROR. Origin Account insufficient founds", HttpStatus.FORBIDDEN);
        } else {
                originAccount.addTransaction(transaction1);
                transactionRepository.save(transaction1);
                accountRepository.save(originAccount);
                targetAccount.addTransaction(transaction2);
                transactionRepository.save(transaction2);
                accountRepository.save(targetAccount);
                return new ResponseEntity<>("Transaction Success", HttpStatus.CREATED);
        }


        


        //transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());
        //return

    }
}