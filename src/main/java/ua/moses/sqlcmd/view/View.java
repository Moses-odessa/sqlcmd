package ua.moses.sqlcmd.view;

public interface View {
    String read ();
    void write(String s);
    void writeError(String s);
    void writeTable(String[] s);
}
