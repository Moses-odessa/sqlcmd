package ua.moses.sqlcmd.controller;

import ua.moses.sqlcmd.controller.command.*;
import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;
import static java.lang.Thread.sleep;

public class MainController {
    public static final String EXIT_COMMAND = "exit";
    public static final String HELP_COMMAND = "help";
    public static final String GREETING = "Добро пожаловать!";
    public static final String PARTING = "До скорой встречи!";
    public static final String COMMAND_PROMPT =
            String.format("------------------------------------------------------------------\n" +
            "Введите нужную комманду или %s для справки (или %s для выхода):", HELP_COMMAND, EXIT_COMMAND);

    private final View view;
    private final DefaultCommand[] commands;

    public MainController(ua.moses.sqlcmd.view.View view, DataBaseManager database) {
        this.view = view;
        this.commands = new DefaultCommand[]{
                new ConnectToDatabase(view, database),
                new ShowAllTables(view, database),
                new CreateTable(view, database),
                new DropTable(view, database),
                new ClearTable(view, database),
                new ShowTableData(view, database),
                new InsertValues(view, database),
                new UpdateRecords(view, database),
                new DeleteRecords(view, database),
                new HelpCommand(view, database),
                new UnknownCommand(view, database)};
    }

    public void run() {
        view.write(GREETING);
        while (true) {
            view.write(COMMAND_PROMPT);
            String[] commandString = view.read().split("\\|");
            String commandName;
            String[] commandParameters = new String[0];
            commandName = commandString[0];
            if (commandString.length > 1) {
                commandParameters = Arrays.copyOfRange(commandString, 1, commandString.length);
            }
            if (commandName.equalsIgnoreCase(EXIT_COMMAND)) {
                view.write(PARTING);
                break;
            }

            for (DefaultCommand command : commands) {
                if (command.check(commandName)) {
                    command.run(commandParameters);
                    break;
                }
            }
            try {
                sleep(100);  // нужно для правильного порядка вывода - еще не знаю как сделать по-другому
            } catch (InterruptedException e) {
                // ничего не делаем
            }
        }
    }
}
