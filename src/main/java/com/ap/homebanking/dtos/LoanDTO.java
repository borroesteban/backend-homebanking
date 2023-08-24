package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Loan;

import java.util.Set;

public class LoanDTO {
    private long id;
    private String name;
    private double maxAmount;
    private Set<Integer> payments;

    public LoanDTO(Loan loan){
        this.id=loan.getId();
        this.name=loan.getName();
        this.maxAmount=loan.getMaxAmount();
        this.payments=loan.getPayments();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }
}
