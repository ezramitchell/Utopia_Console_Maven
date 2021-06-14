package com.ss.cli.handlers.admin;

import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.admin.commands.AddCommand;
import com.ss.cli.handlers.admin.commands.DeleteCommand;
import com.ss.cli.handlers.admin.commands.SearchCommand;
import com.ss.cli.handlers.admin.commands.UpdateCommand;

import java.util.Scanner;

/**
 * Handler for all admin methods, authentication should happen before this handler
 */
public class AdminHandler extends ConsoleHandler {
    public AdminHandler(Scanner input) {
        super(input);
        addCommands();
    }

    @Override
    public void init() {
        System.out.println("Admin menu, help or -h for available commands");
    }


    private void addCommands(){
        registerCommand("search", new SearchCommand().createSearch(), """
                search
                    -flight     table to search
                        -id value
                        -route_id value
                        -airplane_id value
                        -seat_price value
                    -booking
                        -user user_id
                        -id value
                        -is_active default 1, 0 is false
                        -seat_type value
                    -airport
                        -iata_id value
                        -city value use "city name" for multiple words
                    -traveler
                        -id value
                        -username value
                        -phone value
                        -email value
                    -agent
                        -id value
                        -email value""");

        registerCommand("add", new AddCommand(input).createAdd(), """
                add
                    -flight
                    -booking
                    -airport
                    -traveler
                    -agent""");

        registerCommand("delete", new DeleteCommand().createDelete(), """
                delete
                    -traveler
                        -id value
                    -agent
                        -id value
                    -airport
                        -iata_id value
                    -booking
                        -id value
                    -flight
                        -id value""");

        registerCommand("update", new UpdateCommand().createUpdate(), """
                update
                    -traveler
                            -id     value
                            -given_name     value
                            -family_name    value
                            -username   value
                            -email      value
                            -password   value
                            -phone      value
                    -agent
                        -id     value
                        -given_name     value
                        -family_name    value
                        -username   value
                        -email      value
                        -password   value
                        -phone      value
                    -airport
                        -id     id to update
                        -iata_id     new id
                        -city   "city"
                    -booking
                        -id     value
                        -active     1 or 0
                        -confirmation   value
                        -seat_type  value
                    -flight
                        -id     value
                        -route  route_id
                        -airplane   id
                        -departure      yyyy-MM-dd HH:mm:ss local timezone""");
    }
}
