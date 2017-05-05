package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class ConnectToDatabase extends DefaultCommand {

    public ConnectToDatabase(View view, DataBaseManager database) {
        super(view, database, "connect", 3, 3);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length)) {
            String databaseName = parameters[0];
            String userName = parameters[1];
            String userPassword = parameters[2];
            try {
                database.connect(databaseName, userName, userPassword);
                view.write(String.format("Подключение к базе данных %s с пользователем %s прошло успешно", databaseName, userName));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        }
    }

    public String help() {
        return "connect - подключение к базе данных. Формат комманды:\n" +
                "\tconnect|database|username|userpassword - где database - название базы данных,\n" +
                "\tusername - имя пользователя, userpassword - пароль\n";
    }
}
