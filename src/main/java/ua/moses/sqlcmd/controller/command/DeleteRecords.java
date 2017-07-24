package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class DeleteRecords extends DefaultCommand {

    public DeleteRecords(View view, DataBaseManager database) {
        super(view, database, "delete", "delete|tablename|criteriacolumn|criteriavalue",
                "удаление записей в таблице,\n" +
                        "\tгде tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора",
                3, 3);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && checkConnection()) {
            String tabledName = parameters[0];
            String criteriaColumn = parameters[1];
            String criteriaValue = parameters[2];
            try {
                int deletedRecords = database.deleteRecord(tabledName, criteriaColumn, criteriaValue);
                view.write(String.format("Количество удаленных записей в таблице %s: %s", tabledName, deletedRecords));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        }
    }
}
