package ua.moses.sqlcmd.view;

import ua.moses.sqlcmd.model.Record;

import java.util.List;

class PrintTable {
    static String convertToString(List<Record> tableData) {
        StringBuilder result = new StringBuilder();
        int[] maxColumnsSize = getMaxColumnsSize(tableData);
        result.append(printHorisontalLine(maxColumnsSize,
                "┌", "─", "┐", "┬")).append("\n");
        result.append(printRow(tableData.get(0), maxColumnsSize)).append("\n");
        result.append(printHorisontalLine(maxColumnsSize,
                "├", "─", "┤", "┼")).append("\n");
        for (int i = 1; i < tableData.size(); i++) {
            result.append(printRow(tableData.get(i), maxColumnsSize)).append("\n");
        }
        result.append(printHorisontalLine(maxColumnsSize,
                "└", "─", "┘", "┴"));
        return result.toString();
}

    private static int[] getMaxColumnsSize(List<Record> tableData) {
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

    private static StringBuilder printRow(Record tableRow, int[] maxColumnsSize) {
        StringBuilder result = new StringBuilder();
        result.append("│ ");
        for (int i = 0; i < tableRow.length(); i++) {
            result.append(tableRow.get(i));
            result.append(printMultiSymbol(" ", maxColumnsSize[i] - tableRow.get(i).toString().length()));
            if (i != tableRow.length() - 1) {
                result.append(" │ ");
            }
        }
        result.append(" │");
        return result;
    }

    private static StringBuilder printMultiSymbol(String symbol, int spaceCount) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < spaceCount; i++) {
            result.append(symbol);
        }
        return result;
    }

    private static StringBuilder printHorisontalLine(int[] maxColumnsSize, String startSymbol, String middleSymbol,
                                     String endSymbol, String crossSymbol) {
        StringBuilder result = new StringBuilder(startSymbol);
        for (int i = 0; i < maxColumnsSize.length; i++) {
            if (i != 0) {
                result.append(crossSymbol);
            }
            result.append(printMultiSymbol(middleSymbol, maxColumnsSize[i] + 2));
        }
        result.append(endSymbol);
        return result;
    }
}
