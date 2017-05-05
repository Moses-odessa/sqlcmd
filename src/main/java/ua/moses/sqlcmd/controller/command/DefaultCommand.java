package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public abstract class DefaultCommand {
    protected View view;
    protected DataBaseManager database;
    private final String commandName;
    private final int minParametersCount;
    private final int maxParametersCount;
    final String DEFAULT_ERROR_MESSAGE = "Ошибка выполнения комманды: ";

    DefaultCommand(View view, DataBaseManager database, String commandName, int minParametersCount, int maxParametersCount) {
        this.view = view;
        this.database = database;
        this.commandName = commandName;
        this.minParametersCount = minParametersCount;
        this.maxParametersCount = maxParametersCount;
    }

    public boolean check(String commandName) {
        return commandName.equalsIgnoreCase(this.commandName);
    }

    public abstract void run(String[] parameters);

    public abstract String help();

    boolean checkParametersCount(int count) {
        if (count < this.minParametersCount || count > this.maxParametersCount) {
            view.writeError(String.format("Неверное количество параметров.\n" +
                    "Ожидается от %s до %s, а получено %s.", this.minParametersCount, this.maxParametersCount, count));
            return false;
        } else {
            return true;
        }
    }


}
