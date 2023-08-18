package com.ap.homebanking.repositories;

import com.ap.homebanking.Models.Client;
import com.ap.homebanking.Models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
