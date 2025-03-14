package com.personal.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.project.repository.Balance_repo;

@Service
public class HomeService {

    @Autowired
    private Balance_repo balance_repo;

    public int balance() {
        return balance_repo.get_balance();
    }

}
