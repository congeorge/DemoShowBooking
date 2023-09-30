package com.jpm.moviesystem.utils;

import com.jpm.moviesystem.service.UserService;

import java.util.Scanner;


//TODO : temp util class to get user details and set isAdminUser flag
public class UserControUtils {

    private UserControUtils() {
    }

    public static void getUserLoginDetails() {

        System.out.println("Select 1 for Admin");
        System.out.println("Select 2 for Buyer");
        System.out.print("Enter selected option: ");
        Scanner scanner = new Scanner(System.in);
        String optionSelected = scanner.nextLine();
        if (optionSelected.equals("1")) {
            System.out.println("Username:");
            String name = scanner.nextLine();
            System.out.println("Password:");
            String pass = scanner.nextLine();
            if (UserService.isValidUser(name.toLowerCase(), pass.toLowerCase())) {
                UserService.isAdminUser = true;
            }

        } else
            UserService.isAdminUser = false;
    }
}
