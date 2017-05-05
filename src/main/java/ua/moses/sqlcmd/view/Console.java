package ua.moses.sqlcmd.view;

import java.util.Scanner;

public class Console implements View {

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void write(String s) {
        System.out.println(s);
    }

    public void writeError(String s) {
        System.err.println(s);
    }

    public void writeTable(String[][] tableData) {
        int[] maxColumnsSize = getMaxColumsSize(tableData);
        if (tableData[0].length == 0) {
            System.err.println("Таблица не имеет колонок!");
        } else {
            printHorisontalLine(maxColumnsSize, "┌", "─", "┐", "┬");
            printRow(tableData[0], maxColumnsSize);
            printHorisontalLine(maxColumnsSize, "├", "─", "┤", "┼");
            for (int i = 1; i < tableData.length; i++) {
                printRow(tableData[i], maxColumnsSize);
            }
            printHorisontalLine(maxColumnsSize, "└", "─", "┘", "┴");
        }
    }

    private int[] getMaxColumsSize(String[][] tableData) {
        int[] maxColumnsSize = new int[tableData[0].length];
        for (String[] tableRow : tableData) {
            for (int columnIndex = 0; columnIndex < tableRow.length; columnIndex++) {
                if (tableRow[columnIndex].length() > maxColumnsSize[columnIndex]) {
                    maxColumnsSize[columnIndex] = tableRow[columnIndex].length();
                }
            }
        }
        return maxColumnsSize;
    }

    private void printRow(String[] tableRow, int[] maxColumnsSize) {
        System.out.print("│ ");
        for (int i = 0; i < tableRow.length; i++) {
            System.out.print(tableRow[i]);
            printMultiSymbol(" ", maxColumnsSize[i] - tableRow[i].length());
            if (i != tableRow.length - 1) {
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
