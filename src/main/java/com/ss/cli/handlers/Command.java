package com.ss.cli.handlers;

@FunctionalInterface
public interface Command {
    ConsoleHandler execute(String[] args);
}
