package ua.moses.sqlcmd.controller;

import ua.moses.sqlcmd.controller.command.*;
import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class MainController {
    private static final String EXIT_COMMAND = "exit";
    private View view;
    private Command[] commands;

    MainController(View view, DataBaseManager database) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(view, database),
                new Tables(view, database),
                new Create(view, database),
                new Drop(view, database),
                new Clear(view, database),
                new Find(view, database),
                new Help(view, database),
                new Unknow(view, database)};
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

            for (Command command : commands) {
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
