package ua.moses.sqlcmd;

import ua.moses.sqlcmd.controller.MainController;
import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.model.PostgresManager;
import ua.moses.sqlcmd.view.Console;
import ua.moses.sqlcmd.view.View;

public class Main {

    public static void main(String[] args) {
        View view = new Console();
        DataBaseManager database = new PostgresManager();
        MainController controller = new MainController(view, database);
        controller.run();
    }
}
