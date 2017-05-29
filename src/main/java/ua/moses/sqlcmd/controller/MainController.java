package ua.moses.sqlcmd.controller;

import ua.moses.sqlcmd.controller.command.*;
import ua.moses.sqlcmd.model.DataBaseManager;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class MainController {
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";
    private ua.moses.sqlcmd.view.View view;
    private DefaultCommand[] commands;

    MainController(ua.moses.sqlcmd.view.View view, DataBaseManager database) {
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
        view.write("Добро пожаловать!");
        while (true) {

            view.write(String.format("------------------------------------------------------------------\n" +
                    "Введите нужную комманду или %s для справки (или %s для выхода):", HELP_COMMAND, EXIT_COMMAND));
            String[] commandString = view.read().split("\\|");
            String commandName = "";
            String[] commandParameters = new String[0];
            if (commandString.length > 0) {
                commandName = commandString[0];
            }
            if (commandString.length > 1) {
                commandParameters = Arrays.copyOfRange(commandString, 1, commandString.length);
            }
            if (commandName.equalsIgnoreCase(EXIT_COMMAND)) {
                view.write("До скорой встречи!");
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
