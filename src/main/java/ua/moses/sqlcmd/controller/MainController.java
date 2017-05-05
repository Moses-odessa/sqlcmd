package ua.moses.sqlcmd.controller;

import ua.moses.sqlcmd.controller.command.*;
import ua.moses.sqlcmd.model.DataBaseManager;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class MainController {
    private static final String EXIT_COMMAND = "exit";
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
                new HelpCommand(view, database),
                new UnknowCommand(view, database)};
    }

    public void run() {
        view.write("Добро пожаловать!");
        while (true) {
            view.write("---------------------------------------------\n" +
                    "Введите нужную комманду или help для справки:");
            String[] currentCommand = view.read().split("\\|");
            String commandName = "";
            String[] commandParameters = new String[0];
            if (currentCommand.length > 0) {
                commandName = currentCommand[0];
            }
            if (currentCommand.length > 1) {
                commandParameters = Arrays.copyOfRange(currentCommand, 1, currentCommand.length);
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
