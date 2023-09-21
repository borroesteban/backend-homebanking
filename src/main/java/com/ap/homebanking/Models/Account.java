package com.ap.homebanking.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;




}

