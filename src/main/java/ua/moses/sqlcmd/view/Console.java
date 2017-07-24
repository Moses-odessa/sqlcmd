package ua.moses.sqlcmd.view;

import ua.moses.sqlcmd.model.Record;

import java.util.List;
import java.util.Scanner;

public class Console implements View {

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    synchronized public void write(String s) {
        System.out.println(s);
    }

    synchronized public void writeError(String s) {
        System.err.println(s);
    }

    synchronized public void writeTable(List<Record> tableData) {
        if (tableData.get(0).length() == 0) {
            writeError("Таблица не имеет колонок!");
        } else {
            write(PrintTable.convertToString(tableData));
        }
    }
}
