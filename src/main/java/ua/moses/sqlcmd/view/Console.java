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
            int[] maxColumnsSize = getMaxColumnsSize(tableData);
            printHorisontalLine(maxColumnsSize, "┌", "─", "┐", "┬");
            printRow(tableData.get(0), maxColumnsSize);
            printHorisontalLine(maxColumnsSize, "├", "─", "┤", "┼");
            for (int i = 1; i < tableData.size(); i++) {
                printRow(tableData.get(i), maxColumnsSize);
            }
            printHorisontalLine(maxColumnsSize, "└", "─", "┘", "┴");
        }
    }

    private int[] getMaxColumnsSize(List<Record> tableData) {
        int[] maxColumnsSize = new int[tableData.get(0).length()];
        for (Record tableRow : tableData) {
            for (int columnIndex = 0; columnIndex < tableRow.length(); columnIndex++) {
                if (tableRow.get(columnIndex).toString().length() > maxColumnsSize[columnIndex]) {
                    maxColumnsSize[columnIndex] = tableRow.get(columnIndex).toString().length();
                }
            }
        }
        return maxColumnsSize;
    }

    private void printRow(Record tableRow, int[] maxColumnsSize) {
        System.out.print("│ ");
        for (int i = 0; i < tableRow.length(); i++) {
            System.out.print(tableRow.get(i));
            printMultiSymbol(" ", maxColumnsSize[i] - tableRow.get(i).toString().length());
            if (i != tableRow.length() - 1) {
                System.out.print(" │ ");
            }
        }
        System.out.println(" │");
    }

    private void printMultiSymbol(String symbol, int spaceCount) {
        for (int i = 0; i < spaceCount; i++) {
            System.out.print(symbol);
        }

    }

    private void printHorisontalLine(int[] maxColumnsSize, String startSymbol, String middleSymbol, String endSymbol, String crossSymbol) {
        System.out.print(startSymbol);
        for (int i = 0; i < maxColumnsSize.length; i++) {
            if (i != 0) {
                System.out.print(crossSymbol);
            }
            printMultiSymbol(middleSymbol, maxColumnsSize[i]+2);


        }
        System.out.println(endSymbol);
    }
}
