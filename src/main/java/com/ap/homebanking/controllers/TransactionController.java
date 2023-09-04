package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.dtos.TransactionDTO;
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
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> startTransaction(
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam String originAccountNumber,
            @RequestParam String targetAccountNumber,
            Authentication authentication) {
        if (amount == 0.0) {
            return new ResponseEntity<>("ERROR. Missing data: amount",HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: description",HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: origin account number", HttpStatus.FORBIDDEN);
        }
        if (targetAccountNumber.isEmpty()) {
            return new ResponseEntity<>("ERROR. Missing data: target account number",HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.equals(targetAccountNumber)){
            return new ResponseEntity<>("ERROR. Origin Account and Target Account are the same", HttpStatus.FORBIDDEN);
        }
        


        //transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());
        //return

    }
}