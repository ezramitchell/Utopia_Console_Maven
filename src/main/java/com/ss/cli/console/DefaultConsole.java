package com.ss.cli.console;

import com.ss.cli.handlers.AuthHandler;
import com.ss.cli.handlers.ConsoleHandler;

import java.util.Scanner;
import java.util.Stack;

public class DefaultConsole extends Thread {

    private final Scanner input;
    private final Stack<ConsoleHandler> handlers;
    private boolean retry;
    private int retryCount;

    public DefaultConsole(ConsoleHandler start, Scanner input) {
        this.handlers = new Stack<>();
        handlers.add(start);
        start.init();
        this.input = input;
        retry = false;
        retryCount = 0;
    }

    @Override
    public void run() {
        while (handlers.size() > 0) {
            ConsoleHandler consoleHandler;
            if (retry && retryCount < 4) {
                consoleHandler = handlers.peek().retry();
                retry = false;
                retryCount++;
            } else {
                consoleHandler = handlers.peek().parseCommand(input.nextLine());
                retryCount = 0;
            }

            if (consoleHandler.isFinished()) {
                switch (consoleHandler.getExitCode()) {
                    case 2: //retry
                        retry = true;
                        break;
                    case 1: //failed
                        System.err.println("Command failed");
                        break;
                    case 0: //good
                        break;
                    case -1: //back one
                        handlers.pop();
                        break;
                    case -2: //to auth
                        for (int i = handlers.size() - 1; i > 0; i--) {
                            if (handlers.peek() instanceof AuthHandler) break;
                            else handlers.pop();
                        }
                        break;
                    case -3: //to beginning
                        for (int i = handlers.size() - 1; i > 0; i--) {
                            handlers.pop();
                        }
                        break;
                    case -4: //exit
                        handlers.clear();
                        break;
                }
            } else {
                handlers.push(consoleHandler);
            }
        }
    }
}
