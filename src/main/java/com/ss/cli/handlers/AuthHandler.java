package com.ss.cli.handlers;

import com.ss.cli.handlers.traveler.TravelerHandler;
import com.ss.service.AuthenticationExecutor;
import com.ss.utopia.entity.User;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Authentication handler, functions as base handler
 */
public class AuthHandler extends ConsoleHandler {
    private final AuthenticationExecutor ae;


    public AuthHandler(Scanner input) {
        super(input);
        this.ae = new AuthenticationExecutor();
        addCommands();
    }

    @Override
    public void init() {
        System.out.println("Authentication menu, main command is 'login'\nEnter help or -h to see available commands");
    }

    private void addCommands() {
        //authentication command
        registerCommand("login/auth -email", this::authCommand2, """
                login/auth\tauthenticate yourself
                \tOptions:
                \t-email\tauthenticate with email instead of username""");
    }
    
    private ConsoleHandler authCommand2(String[] args){
        String username = null;
        String password;
        String email = null;

        if (Arrays.asList(args).contains("-email")) {
            System.out.println("Enter email:");
            email = input.nextLine();
        } else {
            System.out.println("Enter username:");
            username = input.nextLine();
        }

        System.out.println("Enter password");
        password = input.nextLine();
        User user = ae.authenticate(username, password, email);

        if (user != null) { //Send to appropriate handler
            switch (user.getRole().getName()) {
                case "Administrator" -> { //
                    //TODO administrator handler
                    System.out.println("Successfully authenticated");
                    return new ExitHandler(ExitHandler.ExitType.NOTHING);
                }
                case "Agent" -> {
                    //TODO agent handler
                    System.out.println("Successfully authenticated");
                    return new ExitHandler(ExitHandler.ExitType.EXIT);
                }
                case "Traveler" -> {
                    System.out.println("Successfully authenticated");
                    return new TravelerHandler(input, user);
                }
            }
        }
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }
}
