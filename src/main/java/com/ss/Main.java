package com.ss;

import com.ss.cli.console.DefaultConsole;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DefaultConsole defaultConsole = new DefaultConsole(in);
        defaultConsole.start();
    }
}
