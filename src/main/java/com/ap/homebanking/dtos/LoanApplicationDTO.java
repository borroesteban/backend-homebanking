package com.ap.homebanking.dtos;

import java.util.Set;

public class LoanApplicationDTO {
    private long id;
    private double amount;
    //private Set<Integer> payments;
    private int payments;
    private String accountToNumber;

    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }

    //public Set<Integer> getPayments() {return payments;}
    public int getPayments() {return payments;}

    public String getAccountToNumber() {
        return accountToNumber;
    }
}


