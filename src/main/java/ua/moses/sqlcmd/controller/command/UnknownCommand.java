package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class UnknownCommand extends DefaultCommand {

    public UnknownCommand(View view, DataBaseManager database) {
        super(view, database, "", "",
                "Данной команды не существует!",
                0, 0);
    }

    public void run(String[] parameters) {
        view.writeError("Данной команды не существует!");
    }


    @Override
    public boolean check(String commandName) {
        return true;
    }

}
