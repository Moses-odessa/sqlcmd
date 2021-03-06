package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;

public class HelpCommand extends DefaultCommand {
    private final DefaultCommand[] commands;

    public HelpCommand(ua.moses.sqlcmd.view.View view, DataBaseManager database) {
        super(view, database, "help", "help или help|command",
                "вызов справки,\n" +
                        "\tпо всем коммандам (при вызове без параметров) или по комманде command",
                0, 1);
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
                this,
                new UnknownCommand(view, database)};
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length)) {
            if (parameters.length == 0) {
                for (int i = 0; i < this.commands.length-1; i++) {
                    this.commands[i].printHelp();
                }
            } else {
                String commandForHelp = parameters[0];
                for (DefaultCommand command : this.commands) {
                    if (command.check(commandForHelp)){
                        command.printHelp();
                        break;
                    }
                }
            }
        }
    }
}
