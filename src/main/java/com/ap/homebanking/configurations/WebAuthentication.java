package com.ap.homebanking.configurations;

import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    ClientRepository clientRepository;
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(inputName->{
            Client client = clientRepository.findByEmail(inputName);
            if(client !=null) {
                if (client.getEmail().equals("admin@mindhub.com")){
                return new User(client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList(("ADMIN")));
                }
                return new User(client.getEmail(), client.getPassword(),AuthorityUtils.createAuthorityList("CLIENT"));
            }else{
                throw new UsernameNotFoundException("unknown client:" + inputName);
            }
        });
    }
}
