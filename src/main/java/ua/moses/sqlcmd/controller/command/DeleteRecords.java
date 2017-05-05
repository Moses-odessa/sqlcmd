package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

public class DeleteRecords extends DefaultCommand {


    public DeleteRecords(View view, DataBaseManager database) {
        super(view, database, "delete", 3, 3);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            String criteriaColumn = parameters[1];
            String criteriaValue = parameters[2];
            try {
                int deletedRecords = database.deleteRecord(tabledName, criteriaColumn, criteriaValue);
                view.write(String.format("Количество удаленных записей в таблице %s: %s", tabledName, deletedRecords));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "delete - удаление записей в таблице. Формат комманды:\n" +
                "\tdelete|tablename|criteriacolumn|criteriavalue - где tablename - имя таблицы,\n" +
                "\tcriteriacolumn, criteriavalue - колонка и значение условия отбора\n" ;
    }

}
