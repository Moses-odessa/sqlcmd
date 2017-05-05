package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

public class ShowAllTables extends DefaultCommand {

    public ShowAllTables(View view, DataBaseManager database) {
        super(view, database, "tables", 0, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            try {
                view.write(Arrays.toString(database.getTables()));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "tables - вывод списка таблиц. Формат комманды:\n" +
                "\ttables - вывод списка всех таблиц в подключенной базе данных\n";
    }
}
