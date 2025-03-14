package com.personal.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.project.model.UserPrincipal;
import com.personal.project.model.Users;
import com.personal.project.repository.UsersRepo;

@Service

public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepo user_repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        Users user = user_repo.findByEmail(Email);
        if (user == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

    public Users saveUser(Users user) {
        user.setPassword("{bcrypt}" + encoder.encode(user.getPassword()));
        return user_repo.SaveUser(user);
    }
}
