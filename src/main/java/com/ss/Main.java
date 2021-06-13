package com.ss;

import com.ss.cli.console.DefaultConsole;
import com.ss.cli.handlers.AuthHandler;
import com.ss.service.AuthenticationExecutor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DefaultConsole defaultConsole = new DefaultConsole(new AuthHandler(in, new AuthenticationExecutor()), in);
        defaultConsole.start();
    }
}
