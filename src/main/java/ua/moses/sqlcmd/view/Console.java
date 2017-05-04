package ua.moses.sqlcmd.view;

import java.util.Arrays;
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

    public void writeTable(String[] s) {
        System.out.println(Arrays.toString(s));
    }
}
