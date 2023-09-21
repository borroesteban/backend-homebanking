package com.ap.homebanking.DTOS;

import com.ap.homebanking.Models.Client;
import com.ap.homebanking.Models.ClientLoan;
import com.ap.homebanking.Models.Loan;

import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

public class ClientLoanDTO {
    private long id;
    private double amount;

    private Set<Integer> payments = new HashSet<>();
    //private Client client;
    //private Loan loan;
    private String loanName;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.loanName= clientLoan.getName();
        this.id= clientLoan.getId();
        //this.client=clientLoan.getClient();
        //this.loan= clientLoan.getLoan();
        this.amount=clientLoan.getAmount();
        this.payments=clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

 /*   public Client getClient() {
        return client;
    }*/

//    public Loan getLoan() {
//        return loan;
//    }

    public String getLoanName() {
        return loanName;
    }
}
