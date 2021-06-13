package com.ss.cli.handlers;

public class ExitHandler extends ConsoleHandler {
    public ExitHandler(ExitType exit) {
        super(exit.code);
    }

    @Override
    public void init() {}

    public enum ExitType {
        RETRY(2),
        FAILED(1),
        NOTHING(0),
        BACK(-1),
        AUTH(-2),
        BEGINNING(-3),
        EXIT(-4);

        public final int code;

        ExitType(int code) {
            this.code = code;
        }
    }
}
