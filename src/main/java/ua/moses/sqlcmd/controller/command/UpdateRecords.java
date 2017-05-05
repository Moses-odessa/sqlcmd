package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

public class UpdateRecords extends DefaultCommand {


    public UpdateRecords(View view, DataBaseManager database) {
        super(view, database, "update", 5, 5);
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
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "update - обновление записей в таблице. Формат комманды:\n" +
                "\tupdate|tablename|criteriacolumn|criteriavalue|setcolumn|setvalue - где tablename - имя таблицы,\n" +
                "\tcriteriacolumn, criteriavalue - колонка и значение условия отбора\n" +
                "\tsetcolumn, setvalue - обновляемая колонка и новое значение для нее\n";
    }
}

