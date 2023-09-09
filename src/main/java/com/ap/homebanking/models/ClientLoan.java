//imports
package com.ap.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//class definition
@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    private String name;

    //constructors
    public ClientLoan(){}
    public ClientLoan(double amount, int payments){
        this.amount=amount;
        this.payments=payments;
    }

    //relationships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan")
    private Loan loan;


    //getter&setters
    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setClient(Client client) {
        client.addClientLoan(this);
    }

    public void setLoan(Loan loan) {
        loan.setClientLoans(this);
    }
}

