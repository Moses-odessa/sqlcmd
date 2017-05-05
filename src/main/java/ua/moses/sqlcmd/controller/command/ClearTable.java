package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class ClearTable extends DefaultCommand {


    public ClearTable(View view, DataBaseManager database) {
        super(view, database, "clear", 1, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            try {
                database.clearTable(tabledName);
                view.write(String.format("Таблица %s успешно очищена", tabledName));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "clear - очистка всех данных в таблице. Формат комманды:\n" +
                "\tclear|tablename - где tablename - имя очищаемой таблицы\n";
    }
}
