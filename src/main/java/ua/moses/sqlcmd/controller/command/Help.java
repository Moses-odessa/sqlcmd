package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class Help extends DefaultCommand {


    public Help(View view, DataBaseManager database) {
        super(view, database, "help", 0, 1);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length)) {
            if (parameters.length==0){
                view.write(Connect.help());
                view.write(Tables.help());
                view.write(Create.help());
                view.write(Drop.help());
                view.write(Clear.help());
                view.write(Help.help());
            } else {
                String commandForHelp = parameters[0];
                switch (commandForHelp){
                    case "connect": view.write(Connect.help());
                        break;
                    case "tables": view.write(Tables.help());
                        break;
                    case "create": view.write(Create.help());
                        break;
                    case "drop": view.write(Drop.help());
                        break;
                    case "clear": view.write(Clear.help());
                        break;
                    case "help": view.write(Help.help());
                        break;
                    default: view.writeError(String.format("Команды %s не существует!", commandForHelp));
                }
            }

        }
    }

    private static String help() {
        return "help - вызов справки. Формат комманды:\n" +
                "\thelp - вывод справки по всем коммандам\n" +
                "\thelp|command - вывод справки по комманде command\n";
    }
}
