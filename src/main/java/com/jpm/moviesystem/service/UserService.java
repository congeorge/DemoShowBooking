package com.jpm.moviesystem.service;


import com.jpm.moviesystem.model.User;
import java.util.List;


public class UserService {

    //
    private static User admin = new User("admin", "admin", "admin");
    private static final List<User> validUsers = List.of(admin);

    public static boolean isAdminUser=false;

    public static boolean isValidUser(String user,String password) {
        return validUsers.stream().anyMatch(u -> u.getUserName().equals(user) && u.getPassword().equals(password));
      }

}
