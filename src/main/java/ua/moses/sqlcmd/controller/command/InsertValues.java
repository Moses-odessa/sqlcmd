package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

import java.util.Arrays;

public class InsertValues extends DefaultCommand {


    public InsertValues(View view, DataBaseManager database) {
        super(view, database, "insert", 3, 201);
    }

    public void run(String[] parameters) {
        if (checkParametersCount(parameters.length) && database.isConnected()) {
            String tabledName = parameters[0];
            int valuesCount = (parameters.length - 1) / 2;
            String[] columns = new String[valuesCount];
            String[] values = new String[valuesCount];
            for (int i = 0; i < valuesCount; i++) {
                columns[i] = parameters[i * 2 + 1];
                values[i] = parameters[i * 2 + 2];
            }
            try {
                database.insertRecord(tabledName, columns, values);
                view.write(String.format("Запись в таблицу %s успешно добавлена", tabledName));
            } catch (RuntimeException e) {
                view.writeError(DEFAULT_ERROR_MESSAGE + e.getMessage());
            }
        } else if (!database.isConnected()) {
            view.writeError("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!");
        }
    }

    public String help() {
        return "insert - создание новой таблицы с заданными полями. Формат комманды:\n" +
                "\tinsert|tablename|column1|value1|column2|value2|... - где tablename - имя таблицы,\n" +
                "\tcolumn1, column2, и т.д - названия записываемых полей (не больше ста)\n" +
                "\tvalue1, value2, и т.д - соответственно записываемые значения)\n";
    }

    @Override
    boolean checkParametersCount(int count) {
        return super.checkParametersCount(count) && checkNotPairParameters(count);
    }

    private boolean checkNotPairParameters(int count) {
        if ((count % 2) == 0) { //учитывая, что первое значение - имя таблицы, должно быть непарное общее количество параметров
            view.writeError("Непарное значение имен колонок и записываемых значений!");
            return false;
        } else {
            return true;
        }

    }
}

