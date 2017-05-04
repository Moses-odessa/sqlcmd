package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class Drop extends DefaultCommand {


    public Drop(View view, DataBaseManager database) {
        super(view, database, "drop", 1, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            try {
                database.dropTable(tabledName);
                view.write(String.format("Таблица %s успешно удалена", tabledName));
            } catch (Exception e) {
                view.writeError(e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    static String help() {
        return "drop - удаление таблицы из базы данных. Формат комманды:\n" +
                "\tdrop|tablename - где tablename - имя удаляемой таблицы\n";
    }
}
