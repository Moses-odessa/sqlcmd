package ua.moses.sqlcmd.model;

public interface Record {
    Object get(int index);
    void set (int index, Object data);
    int length();
    Object[] toArray();
}
