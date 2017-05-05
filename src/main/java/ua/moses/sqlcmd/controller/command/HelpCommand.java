package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;

public class HelpCommand extends DefaultCommand {
    private final DefaultCommand[] commands;

    public HelpCommand(ua.moses.sqlcmd.view.View view, DataBaseManager database) {
        super(view, database, "help", 0, 1);
        this.commands = new DefaultCommand[]{
                new ConnectToDatabase(view, database),
                new ShowAllTables(view, database),
                new CreateTable(view, database),
                new DropTable(view, database),
                new ClearTable(view, database),
                new ShowTableData(view, database),
                new InsertValues(view, database),
                new UpdateRecords(view, database),
                new DeleteRecords(view, database),
                this,                       //сам HelpCommand
                new UnknownCommand(view, database)};
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length)) {
            if (parameters.length == 0) {
                for (int i = 0; i < this.commands.length-1; i++) {//кроме последнего, который UnknownCommand
                    view.write(this.commands[i].help());
                }
            } else {
                String commandForHelp = parameters[0];
                for (DefaultCommand command : this.commands) {
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
