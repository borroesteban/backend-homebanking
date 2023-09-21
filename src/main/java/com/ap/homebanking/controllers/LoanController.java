package com.ap.homebanking.controllers;

import com.ap.homebanking.HomebankingApplication;
import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.LoanApplicationDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @RequestMapping("/loans")//get
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }


    @RequestMapping("/loans/{id}")
    private LoanDTO getId(@PathVariable Long id) {
        return new LoanDTO(loanRepository.findById(id).orElse(null));
    }


    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> requestLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {


        //data from requestBody
        Loan loanType = loanRepository.findById(loanApplicationDTO.getId()).orElse(null);
        int payments = loanApplicationDTO.getPayments();
        double amount = loanApplicationDTO.getAmount();
        String accountTo = loanApplicationDTO.getAccountToNumber();
        Account repoAccountTo = accountRepository.findByNumber(accountTo);

        //data from authentication
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> authUserAccounts= client.getAccounts();



        if (amount <= 0.0) {
            return new ResponseEntity<>("Amount error", HttpStatus.FORBIDDEN);
        }

        if (accountTo.isEmpty()) {
            return new ResponseEntity<>("Account to Number error", HttpStatus.FORBIDDEN);
        }

        if (payments == 0) {
            return new ResponseEntity<>("Payments is empty", HttpStatus.FORBIDDEN);
        }

        if (loanType == null) {
            return new ResponseEntity<>("Loan type existence error", HttpStatus.FORBIDDEN);
        }

        if (amount > loanType.getMaxAmount()) {
            return new ResponseEntity<>("Max Amount error", HttpStatus.FORBIDDEN);
        }

        if (loanType.getPayments().contains(payments)){
            return new ResponseEntity<>("Payments error",HttpStatus.FORBIDDEN);
        }

        if(accountRepository.findByNumber(accountTo)==null){
            return new ResponseEntity<>("accountTo doesn't exist",HttpStatus.FORBIDDEN);
        }

        if(!authUserAccounts.contains(repoAccountTo)){
            return new ResponseEntity<>("user doesn't own repoAccountTo",HttpStatus.FORBIDDEN);
        }


        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.20, payments);
        Transaction loanTransaction = null;

        loanTransaction = new Transaction(
                TransactionType.Credit,
                amount,
                loanType.getName() + "loan approved",
                LocalDateTime.now());

        repoAccountTo.setBalance(repoAccountTo.getBalance() + amount);

        client.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);
        accountRepository.save(repoAccountTo);
        clientRepository.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

