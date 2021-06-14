package com.ss.cli.handlers;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ConsoleHandler {
    private boolean finished;
    private int exitCode;
    private String lastCommand;
    private HashMap<String, Command> commands;
    private ArrayList<String> commandInfo;
    protected Scanner input;

    public ConsoleHandler(Scanner input) {
        commands = new HashMap<>();
        finished = false;
        exitCode = 0;
        commandInfo = new ArrayList<>();
        this.input = input;
        addDefaultCommands();
    }

    protected ConsoleHandler(int exitCode) {
        this.exitCode = exitCode;
        finished = true;
    }

    /**
     * Adds default commands, listed in comments
     */
    private void addDefaultCommands(){
        //back
        registerCommand("back", args -> new ExitHandler(ExitHandler.ExitType.BACK),
                "back\tback one menu\n\tOptions: none");
        //exit
        registerCommand("exit", args -> new ExitHandler(ExitHandler.ExitType.EXIT),
                "exit\tcloses program\n\tOptions: none");
        //start
        registerCommand("start", args -> new ExitHandler(ExitHandler.ExitType.BEGINNING),
                "start\treturns to first menu\n\tOptions: none");
        //toauth
        registerCommand("menu", args -> new ExitHandler(ExitHandler.ExitType.AUTH),
                "menu\treturns to menu after authentication\n\tOptions: none");
        //list/ls
        registerCommand("list/ls", args -> {
            for (String s : commands.keySet()) {
                System.out.println(s);
            }
            return new ExitHandler(ExitHandler.ExitType.NOTHING);
        }, "list/ls\tlists available commands in menu\n\tOptions: none");
        //help
        registerCommand("help/-h", args -> {
            for (String s : commandInfo) {
                System.out.println(s);
            }
            return new ExitHandler(ExitHandler.ExitType.NOTHING);
        }, "help/-h\tlists command descriptions in menu\n\tOptions: none");
    }
    /**
     * Display any pertinent data
     */
    public abstract void init();

    /**
     * Reruns last command
     * @return next ConsoleHandler
     */
    public ConsoleHandler retry(){
        return parseCommand(lastCommand);
    }

    /**
     * Run command, returns new handler. Handler exit codes specify correct action
     *
     * @return Handler
     */
    public ConsoleHandler parseCommand(String commandString) {
        lastCommand = commandString;
        //command structure is name/altName -param value
        //split by ' '

        List<String> tokens = Arrays.stream(commandString.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
                .map(s -> s.replace("\"", "")).toList(); //this isn't my regex

        if(tokens.size() > 0){
            String command = tokens.get(0);
            //find command in registered commands
            String match = commands.keySet().stream().filter(s -> s.contains(command)).findFirst().orElse("invalid");

            if(match.equals("invalid")){
                return new ExitHandler(ExitHandler.ExitType.FAILED);
            }

            //execute command
            return commands.get(match).execute(tokens.subList(1, tokens.size()).toArray(new String[0]));
        }
        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    /**
     * Register command function at val
     *
     * @param val console input that triggers command
     * @param command command to register
     */
    protected void registerCommand(String val, Command command, String description) {
        commands.put(val, command);
        commandInfo.add(description);
    }

    public boolean isFinished() {
        return finished;
    }

    protected ConsoleHandler setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public int getExitCode() {
        return exitCode;
    }

    protected ConsoleHandler setExitCode(int exitCode) {
        this.exitCode = exitCode;
        return this;
    }
}
