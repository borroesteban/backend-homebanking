package com.ap.homebanking.Controllers;

import com.ap.homebanking.DTOS.ClientDTO;
import com.ap.homebanking.DTOS.LoanDTO;
import com.ap.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }
    @RequestMapping("/{id}/loans")
    private LoanDTO getId(@PathVariable Long id) {
        return new LoanDTO(loanRepository.findById(id).orElse(null));
    }
}
