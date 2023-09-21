package com.ap.homebanking.dtos;

import com.ap.homebanking.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;

public class ClientLoanDTO {
    private long id;
    private double amount;
    private int payments;
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

    public int getPayments() {
        return payments;
    }

    public String getLoanName() {
        return loanName;
    }
}
