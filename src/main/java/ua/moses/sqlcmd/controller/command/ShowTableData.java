package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class ShowTableData extends DefaultCommand {


    public ShowTableData(View view, DataBaseManager database) {
        super(view, database, "show", 1, 2);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            String sortColumn = "";
            if (parameters.length > 1){
                sortColumn = parameters[1];
            }
            try {
                view.writeTable(database.getTableData(tabledName, sortColumn));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "show - вывод содержимого таблицы. Формат комманды:\n" +
                "\tshow|tablename|sortcolumn - где tablename - имя нужной таблицы\n" +
                "sortcolumn - имя колонки по которой будет отсортирована таблица\n" +
                "(если опущено - без сортировки)";
    }
}
