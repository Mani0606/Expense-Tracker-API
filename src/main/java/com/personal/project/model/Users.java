package com.personal.project.model;

import org.springframework.stereotype.Component;

import lombok.*;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int id;
    private String Email;
    private String username;
    private String password;
}
