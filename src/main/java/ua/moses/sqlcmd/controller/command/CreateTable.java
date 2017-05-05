package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

public class CreateTable extends DefaultCommand {

    public CreateTable(View view, DataBaseManager database) {
        super(view, database, "create", 1, 101);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            String[] columns = Arrays.copyOfRange(parameters, 1, parameters.length);
            try {
                database.createTable(tabledName, columns);
                view.write(String.format("Таблица %s успешно создана", tabledName));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "create - создание новой таблицы с заданными полями. Формат комманды:\n" +
                "\tcreate|tablename|column1|column2|column3|... - где tablename - имя таблицы,\n" +
                "\tcolumn1, column2, column3 и т.д - названия ее полей (не больше ста)\n";
    }
}
