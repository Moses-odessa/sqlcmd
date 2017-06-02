package ua.moses.sqlcmd.model;

import java.util.Arrays;

public class MyRecord implements Record {
    private Object[] values;

    public MyRecord(int size) {
        this.values = new Object[size];
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= values.length){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return values[index];
    }

    @Override
    public void set(int index, Object data) {
        checkIndex(index);
        values[index] = data;
    }

    @Override
    public int length() {
        return values.length;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.values, this.values.length);
    }
}
