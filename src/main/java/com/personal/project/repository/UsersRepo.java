package com.personal.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.personal.project.model.Users;

@Repository
public class UsersRepo {

    @Autowired
    private JdbcTemplate jdbctemp;

    public Users SaveUser(Users user) {
        String query = "INSERT INTO Users (email, username, password) VALUES (?, ?, ?)";

        int rows = jdbctemp.update(query, user.getEmail(), user.getUsername(), user.getPassword());

        System.out.println(rows + " rows Effected");

        return user;
    }

    public Users findByEmail(String Email) {
        String query = "select * from Users where email=(?)";

        System.out.println(Email + "2");

        @SuppressWarnings("deprecation")
        Users user = jdbctemp.queryForObject(query, new Object[] { Email }, (rs, rowNum) -> new Users(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password")));
        return user;
    }

    public int extract_user_id(String Email) {
        String query = "select id from Users where email=(?)";
        @SuppressWarnings("deprecation")
        int id = jdbctemp.queryForObject(query, new Object[] { Email }, Integer.class);
        return id;
    }

}
