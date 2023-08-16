package com.ap.homebanking.Controllers;
import com.ap.homebanking.DTOS.AccountDTO;
import com.ap.homebanking.DTOS.ClientDTO;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        }
    @RequestMapping("/clients/{id}")
    private ClientDTO getId(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
        }
}
