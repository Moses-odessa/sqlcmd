package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class UpdateRecords extends DefaultCommand {


    public UpdateRecords(View view, DataBaseManager database) {
        super(view, database, "update", "update|tablename|criteriacolumn|criteriavalue|setcolumn|setvalue",
                "обновление записей в таблице,\n" +
                        "\tгде tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора,\n" +
                        "\tsetcolumn, setvalue - обновляемая колонка и новое значение для нее",
                5, 5);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            String criteriaColumn = parameters[1];
            String criteriaValue = parameters[2];
            String setColumn = parameters[3];
            String setValue = parameters[4];
            try {
                int updatedRecords = database.updateRecord(tabledName, criteriaColumn, criteriaValue, setColumn, setValue);
                view.write(String.format("Количество обновленных записей в таблице %s: %s", tabledName, updatedRecords));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError(NOT_CONNECT_ERROR_MESSAGE);
        }
    }

}

