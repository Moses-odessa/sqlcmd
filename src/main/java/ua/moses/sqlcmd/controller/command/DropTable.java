package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class DropTable extends DefaultCommand {

    public DropTable(View view, DataBaseManager database) {
        super(view, database, "drop", "drop|tablename",
                "удаление таблицы из базы данных,\n" +
                        "\tгде tablename - имя удаляемой таблицы",
                1, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && checkConnection()) {
            String tabledName = parameters[0];
            try {
                database.dropTable(tabledName);
                view.write(String.format("Таблица %s успешно удалена", tabledName));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        }
    }
}
