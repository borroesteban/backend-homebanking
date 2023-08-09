package com.ap.homebanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;


    public Client(){}
    public Client(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @OneToMany(mappedBy="accountOwner", fetch= FetchType.EAGER)
    Set<Account> accounts=new HashSet<>();


    public long getId(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    private String getEmail(){
        return email;
    }
    private void setEmail(String Email){
        this.email = Email;
    }

    public Set<Account> getAccounts(){return accounts;}

    public void addAccount(Account account){
        account.setAccountOwner(this);
        accounts.add(account);
    }

}
