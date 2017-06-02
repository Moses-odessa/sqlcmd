package ua.moses.sqlcmd.view;

import ua.moses.sqlcmd.model.Record;

import java.util.List;

public interface View {
    String read ();
    void write(String s);
    void writeError(String s);
    void writeTable(List<Record> tableData);
}
