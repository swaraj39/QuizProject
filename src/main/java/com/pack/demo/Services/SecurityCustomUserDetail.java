package com.pack.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pack.demo.Repository.UserRepo;

@Service
public class SecurityCustomUserDetail  implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    

}
