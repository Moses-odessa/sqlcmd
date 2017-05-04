package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class Unknow extends DefaultCommand {

    public Unknow(View view, DataBaseManager database) {
        super(view, database, "", 0, 0);
    }

    public void run(String[] parameters) {
        view.writeError("Данной команды не существует!");
    }

    @Override
    public boolean check(String commandName) {
        return true;
    }
}
