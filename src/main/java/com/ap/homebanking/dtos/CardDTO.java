//imports
package com.ap.homebanking.dtos;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardColor;
import com.ap.homebanking.models.CardType;

import java.time.LocalDate;

//class definition
public class CardDTO {
    private long id;
    private String cardholder;
    private CardType cardType;
    private CardColor cardColor;
    private int number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

//constructors

    public CardDTO(Card card){
        this.cardholder=card.getCardholder();
        this.cardType =card.getCardType();
        this.cardColor =card.getCardColor();
        this.number=card.getNumber();
        this.cvv=card.getCvv();
        this.thruDate=card.getThruDate();
        this.fromDate=card.getFromDate();
    }

    public long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
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
