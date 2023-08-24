//imports
package com.ap.homebanking.dtos;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.color;
import com.ap.homebanking.models.type;

import java.time.LocalDate;

//class definition
public class CardDTO {
    private long id;
    private String cardHolder;
    private type type;
    private color color;
    private int number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

//constructors

    public CardDTO(Card card){
        this.id=card.getId();
        this.cardHolder=card.getCardHolder();
        this.type =card.getType();
        this.color =card.getColor();
        this.number=card.getNumber();
        this.cvv=card.getCvv();
        this.thruDate=card.getThruDate();
        this.fromDate=card.getFromDate();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public type getType() {
        return type;
    }

    public color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
