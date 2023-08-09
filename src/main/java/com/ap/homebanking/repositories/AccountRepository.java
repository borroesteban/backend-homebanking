package com.ap.homebanking.repositories;

import com.ap.homebanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long > {
    //List<Account> findByNumber(String number);
}
