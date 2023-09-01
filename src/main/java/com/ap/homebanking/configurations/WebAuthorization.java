package com.ap.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
         .antMatchers(HttpMethod.POST, "/api/login", "/api/logout", "/api/clients").permitAll()
         .antMatchers("/web/index.html","/web/css/style.css", "/web/js/index.js", "/web/img/**").permitAll()
         .antMatchers(HttpMethod.GET, "/api/clients/current").hasAnyAuthority("CLIENT", "ADMIN")
         .antMatchers("/rest/**").hasAuthority("ADMIN")
         .antMatchers("/h2-console").hasAuthority("ADMIN");


/*
                //hasAnyAuthority("CLIENT", "ADMIN")
                //hasAuthority("ADMIN")

                //everyone is allowed
                .antMatchers("/web/index.html","/web/css/style.css", "/web/js/index.js", "/web/img/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()

                 //admin is allowed
                .antMatchers("/manager.html", "/manager.js").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/{id}").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/clients/current").hasAuthority("CLIENT")

                //client is allowed
                //.antMatchers("/**").hasAuthority("CLIENT")
                //.antMatchers("/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/cards").hasAuthority("CLIENT");
 */



        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((request, response, authentication) -> clearAuthenticationAttributes(request));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_FORBIDDEN));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}