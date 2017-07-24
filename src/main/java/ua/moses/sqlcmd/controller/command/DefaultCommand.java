package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public abstract class DefaultCommand {
    final View view;
    final DataBaseManager database;
    private final String commandName;
    private final String commandFormat;
    private final String commandDescription;
    private final int minParametersCount;
    private final int maxParametersCount;
    final String DEFAULT_ERROR_MESSAGE = "Ошибка выполнения комманды: ";

    DefaultCommand(View view, DataBaseManager database, String commandName, String commandFormat,
                   String commandDescription, int minParametersCount, int maxParametersCount) {
        this.view = view;
        this.database = database;
        this.commandName = commandName;
        this.commandFormat = commandFormat;
        this.commandDescription = commandDescription;
        this.minParametersCount = minParametersCount;
        this.maxParametersCount = maxParametersCount;
    }

    public boolean check(String commandName) {
        return commandName.equalsIgnoreCase(this.commandName);
    }

    public abstract void run(String[] parameters);

    void printHelp(){
        view.write(commandFormat + " - " + commandDescription + "\n");
    }

    boolean checkParametersCount(int count) {
        if (count < this.minParametersCount || count > this.maxParametersCount) {
            String expectedParameters;
            if (this.minParametersCount == this.maxParametersCount) {
                expectedParameters = "" + this.minParametersCount;
            } else {
                expectedParameters = String.format("от %s до %s", this.minParametersCount, maxParametersCount);
            }
            view.writeError(String.format("Неверное количество параметров. Ожидается %s, а получено %s.\n" +
                    "Ожидаемый формат комманды: %s.", expectedParameters, count, commandFormat));
            return false;
        } else {
            return true;
        }
    }

    boolean checkConnection(){
        if (database.isConnected()) {
            return true;
        }
        String NOT_CONNECT_ERROR_MESSAGE =
                "Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!";
        view.writeError(NOT_CONNECT_ERROR_MESSAGE);
        return false;
    }
}
