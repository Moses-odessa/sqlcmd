package ua.moses.sqlcmd.controller.command;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.view.View;

public class InsertValues extends DefaultCommand {

    public InsertValues(View view, DataBaseManager database) {
        super(view, database, "insert", "insert|tablename|column1|value1|column2|value2|...",
                "добавление записи в таблицу,\n" +
                        "\tгде tablename - имя таблицы,\n" +
                        "\tcolumn1, column2, и т.д - названия записываемых полей (не больше ста)\n" +
                        "\tvalue1, value2, и т.д - соответственно записываемые значения",
                3, 201);
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
            view.writeError(NOT_CONNECT_ERROR_MESSAGE);
        }
    }

    @Override
    boolean checkParametersCount(int count) {
        return super.checkParametersCount(count) && checkNotPairParameters(count);
    }

    private boolean checkNotPairParameters(int count) {
        if ((count % 2) == 0) { //учитывая, что первое значение - имя таблицы, должно быть непарное общее количество параметров
            view.writeError("Не совпадает количество имен колонок и записываемых значений!");
            return false;
        } else {
            return true;
        }

    }
}

