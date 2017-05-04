package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class Find extends DefaultCommand {


    public Find(View view, DataBaseManager database) {
        super(view, database, "find", 1, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            try {
                view.writeTable(database.getTableData(tabledName));
            } catch (Exception e) {
                view.writeError(e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "find - вывод содержимого таблицы. Формат комманды:\n" +
                "\tfind|tablename - где tablename - имя нужной таблицы\n";
    }
}
