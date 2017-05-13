package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class ShowTableData extends DefaultCommand {


    public ShowTableData(View view, DataBaseManager database) {
        super(view, database, "show", "show|tablename или show|tablename|sortcolumn",
                "вывод содержимого таблицы,\n" +
                        "\tгде tablename - имя нужной таблицы, sortcolumn - имя колонки по которой будет отсортирована таблица\n" +
                        "\t(если опущено - без сортировки)",
                1, 2);
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
            view.writeError(NOT_CONNECT_ERROR_MESSAGE);
        }
    }

}
