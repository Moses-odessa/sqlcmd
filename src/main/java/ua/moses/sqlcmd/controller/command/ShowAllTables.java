package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class ShowAllTables extends DefaultCommand {

    public ShowAllTables(View view, DataBaseManager database) {
        super(view, database, "tables", "tables",
                "вывод списка всех таблиц в подключенной базе данных.",
                0, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && checkConnection()) {
            try {
                view.write(database.getTables().toString());
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        }
    }
}
