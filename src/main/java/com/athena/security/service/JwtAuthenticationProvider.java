package com.athena.security.service;

import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Tommy on 2017/3/26.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtAuthenticationProvider(AccountService accountService, PasswordEncoder passEncoder){
        this.accountService = accountService;
        this.passwordEncoder = passEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Account intendAccount = (Account) authentication.getPrincipal();
        Account actualAccount = accountService.loadAccountById(intendAccount.getId());
        if (actualAccount.getPassword().equals(passwordEncoder.encode(intendAccount.getPassword()))) {
            //If the account matches
            authentication.setAuthenticated(true);
            return new JwtAuthentication((Account) authentication.getPrincipal());
        }
        else{
            throw new BadCredentialsException("Password doesn't match");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}