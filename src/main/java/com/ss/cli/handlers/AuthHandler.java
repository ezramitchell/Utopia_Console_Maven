package com.ss.cli.handlers;

import com.ss.service.AuthenticationExecutor;
import com.ss.utopia.entity.User;

import java.util.Arrays;
import java.util.Scanner;

public class AuthHandler extends ConsoleHandler{
    private final AuthenticationExecutor ae;

    public AuthHandler(Scanner input, AuthenticationExecutor ae) {
        super(input);
        this.ae = ae;
    }

    @Override
    public void init() {
        System.out.println("Authentication/tutorial menu enter help or -h to see available commands");
        addCommands();
    }

    private void addCommands(){
        //authenticate command
        registerCommand("login/auth -email", args -> {
            String username = null;
            String password;
            String email = null;

            if(Arrays.asList(args).contains("-email")){
                System.out.println("Enter email:");
                email = input.nextLine();
            } else{
                System.out.println("Enter username:");
                username = input.nextLine();
            }

            System.out.println("Enter password");
            password = input.nextLine();
            User user = ae.authenticate(username, password, email);

            if(user != null){
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
                        //TODO traveler handler
                        System.out.println("Successfully authenticated");
                        return new ExitHandler(ExitHandler.ExitType.BEGINNING);
                    }
                }
            }
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }, """
                login/auth\tauthenticate yourself
                \tOptions:
                \t-email\tauthenticate with email instead of username""");
    }
}
