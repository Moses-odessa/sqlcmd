package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class Help extends DefaultCommand {
    private final Command[] commands;

    public Help(View view, DataBaseManager database) {
        super(view, database, "help", 0, 1);
        this.commands = new Command[]{
                new Connect(view, database),
                new Tables(view, database),
                new Create(view, database),
                new Drop(view, database),
                new Clear(view, database),
                new Find(view, database),
                this,                       //сам Help
                new Unknow(view, database)};
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length)) {
            if (parameters.length == 0) {
                for (int i = 0; i < this.commands.length-1; i++) {//кроме последнего, который Unknow
                    view.write(this.commands[i].help());
                }
            } else {
                String commandForHelp = parameters[0];
                for (Command command : this.commands) {
                    if (command.check(commandForHelp)){
                        view.write(command.help());
                        break;
                    }
                }
            }

        }
    }

    public String help() {
        return "help - вызов справки. Формат комманды:\n" +
                "\thelp - вывод справки по всем коммандам\n" +
                "\thelp|command - вывод справки по комманде command\n";
    }
}
