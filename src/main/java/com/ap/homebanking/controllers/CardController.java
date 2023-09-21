package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(toList());
    }
    @RequestMapping("/{id}/cards")
    private CardDTO getId(@PathVariable Long id) {
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }
/*@RequestMapping("/cards/{id}")
private CardDTO getId(@PathVariable Long id) {
    return new CardDTO(cardRepository.findById(id).orElse(null));
}*/
}
