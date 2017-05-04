package ua.moses.sqlcmd.controller.command;

public interface Command {
    boolean check (String commandName);
    void run(String[] parameters);
}
