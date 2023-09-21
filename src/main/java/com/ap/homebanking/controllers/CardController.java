package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.color;
import com.ap.homebanking.models.type;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static com.ap.homebanking.models.type.CREDIT;
import static com.ap.homebanking.models.type.DEBIT;
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

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,
            @RequestParam color cardColor, @RequestParam type cardType){
        Client authUser;
        authUser=clientRepository.findByEmail(authentication.getName());
        Long debit= authUser.getCards().stream().filter(card -> card.getType().equals(CREDIT)).count();
        Long credit=authUser.getCards().stream().filter(card -> card.getType().equals(DEBIT)).count();

         if(debit==3 || credit ==3){return new ResponseEntity<>(HttpStatus.FORBIDDEN);}
         else{
             Random random = new Random();
             int group1 = random.nextInt(9999) + 1;
             int group2 = random.nextInt(9999) + 1;
             int group3 = random.nextInt(9999) + 1;
             int group4 = random.nextInt(9999) + 1;
             int cvv=  random.nextInt(999) + 1;
             String group1String= Integer.toString(group1);
             String group2String= Integer.toString(group2);
             String group3String= Integer.toString(group3);
             String group4String= Integer.toString(group4);
             String cardNumber= group1String + "-" + group2String + "-" + group3String + "-" + group4String;

             Card card=new Card(authUser.getFirstName()+" "+authUser.getLastName(), cardType, cardColor,cardNumber, cvv,
                     LocalDate.now().plusYears(5), LocalDate.now(),authUser);
             cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
         }
    }

    @Autowired
    ClientRepository clientRepository;
    public ClientDTO getAuthUser(Authentication authentication) {
        ClientDTO authUser;
        authUser = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return authUser;
    }

}
