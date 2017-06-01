package integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.moses.sqlcmd.controller.Main;
import ua.moses.sqlcmd.controller.MainController;
import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.model.PostgresManager;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class IntegrationTest {
    private final String DATABASE_NAME = "postgres";
    private final String DATABASE_USER = "postgres";
    private final String DATABASE_PASSWORD = "tend";
    private ConsoleMock console = new ConsoleMock();
    private final String TEST_TABLE_NAME = "dkmbcsryfdkmnbvytkk";
    private final String TABLE_FOR_MANIPULATION = "qikmdbrtbloparwecq";

    @Before
    public void setup() {
        DataBaseManager tempBase = new PostgresManager();
        tempBase.connect(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
        tempBase.createTable(TABLE_FOR_MANIPULATION, new String[]{"column1", "column2"});
    }

    @After
    public void end() {
        DataBaseManager tempBase = new PostgresManager();
        tempBase.connect(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
        tempBase.dropTable(TABLE_FOR_MANIPULATION);
    }

    private void assertEqualsWithConsoleOut(String commandOut) {
        String start = MainController.GREETING + "\n" +
                String.format(MainController.COMMAND_PROMPT, MainController.HELP_COMMAND, MainController.EXIT_COMMAND) + "\n";
        String end = MainController.PARTING;

        assertEquals(start + commandOut + end,
                console.getOut().trim().replaceAll("\r", ""));
    }

    @Test
    public void testExit() {
        // given
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("");
    }

    @Test
    public void testHelpAndExit() {
        // given
        console.addIn("help");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("connect|database|username|userpassword - подключение к базе данных, \n" +
                "\tгде database - название базы данных, username - имя пользователя, userpassword - пароль\n" +
                "\n" +
                "tables - вывод списка всех таблиц в подключенной базе данных.\n" +
                "\n" +
                "create|tablename|column1|column2|column3|... - создание новой таблицы с заданными полями,\n" +
                "\tгде tablename - имя таблицы; column1, column2, column3 и т.д - названия ее полей (не больше ста)\n" +
                "\n" +
                "drop|tablename - удаление таблицы из базы данных,\n" +
                "\tгде tablename - имя удаляемой таблицы\n" +
                "\n" +
                "clear|tablename - очистка всех данных в таблице,\n" +
                "\tгде tablename - имя очищаемой таблицы\n" +
                "\n" +
                "show|tablename или show|tablename|sortcolumn - вывод содержимого таблицы,\n" +
                "\tгде tablename - имя нужной таблицы, sortcolumn - имя колонки по которой будет отсортирована таблица\n" +
                "\t(если опущено - без сортировки)\n" +
                "\n" +
                "insert|tablename|column1|value1|column2|value2|... - добавление записи в таблицу,\n" +
                "\tгде tablename - имя таблицы,\n" +
                "\tcolumn1, column2, и т.д - названия записываемых полей (не больше ста)\n" +
                "\tvalue1, value2, и т.д - соответственно записываемые значения\n" +
                "\n" +
                "update|tablename|criteriacolumn|criteriavalue|setcolumn|setvalue - обновление записей в таблице,\n" +
                "\tгде tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора,\n" +
                "\tsetcolumn, setvalue - обновляемая колонка и новое значение для нее\n" +
                "\n" +
                "delete|tablename|criteriacolumn|criteriavalue - удаление записей в таблице,\n" +
                "\tгде tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора\n" +
                "\n" +
                "help или help|command - вызов справки,\n" +
                "\tпо всем коммандам (при вызове без параметров) или по комманде command\n" +
                "\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testHelpForConnectExit() {
        // given
        console.addIn("help|connect");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("connect|database|username|userpassword - подключение к базе данных, \n" +
                "\tгде database - название базы данных, username - имя пользователя, userpassword - пароль\n" +
                "\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testHelpForUnknowCommandExit() {
        // given
        console.addIn("help|abracadabra");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut(" - Данной команды не существует!\n" +
                "\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Подключение к базе данных " + DATABASE_NAME + " с пользователем " + DATABASE_USER + " прошло успешно\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectBadPasswordAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + "lkdfgjlfjgldsfgldsfjg;ljsdfl;kg");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Ошибка выполнения комманды: ВАЖНО: пользователь \"" + DATABASE_USER + "\" не прошёл проверку подлинности (по паролю) (pgjdbc: autodetected server-encoding to be Cp1251, if the message is not readable, please check database logs and/or host, port, dbname, user, password, pg_hba.conf)\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectBadDatabaseAndExit() {
        // given
        console.addIn("connect|" + "lkdfgjlfjgldsfgldsfjg;ljsdfl;kg" + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Ошибка выполнения комманды: ВАЖНО: база данных \"lkdfgjlfjgldsfgldsfjg;ljsdfl;kg\" не существует (pgjdbc: autodetected server-encoding to be Cp1251, if the message is not readable, please check database logs and/or host, port, dbname, user, password, pg_hba.conf)\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectBadCountParametersAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER);
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Неверное количество параметров. Ожидается 3, а получено 2.\n" +
                "Ожидаемый формат комманды: connect|database|username|userpassword.\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectShowTablesAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("tables");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertThat(console.getOut(), containsString(TABLE_FOR_MANIPULATION));
    }

    @Test
    public void testShowTablesWithoutConnectAndExit() {
        // given
        console.addIn("tables");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Для выполнения этой комманды нужно подключиться к базе данных используя комманду connect!\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testUnknowCommandAndExit() {
        // given
        console.addIn("abracadabra|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Данной команды не существует!\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectInsertWithNotPairParametersAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("insert|" + TABLE_FOR_MANIPULATION + "|column1|value1|column2");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Подключение к базе данных " + DATABASE_NAME + " с пользователем " + DATABASE_USER + " прошло успешно\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Не совпадает количество имен колонок и записываемых значений!\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectCreateTableShowTableDataDeleteTableAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("create|" + TEST_TABLE_NAME + "|column1|column2");
        console.addIn("show|" + TEST_TABLE_NAME);
        console.addIn("drop|" + TEST_TABLE_NAME);
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Подключение к базе данных " + DATABASE_NAME + " с пользователем " + DATABASE_USER + " прошло успешно\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Таблица " + TEST_TABLE_NAME + " успешно создана\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "┌─────────┬─────────┐\n" +
                "│ column1 │ column2 │\n" +
                "├─────────┼─────────┤\n" +
                "└─────────┴─────────┘\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Таблица " + TEST_TABLE_NAME + " успешно удалена\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

    @Test
    public void testConnectInsertUpdateDeleteValuesClearAndExit() {
        // given
        console.addIn("connect|" + DATABASE_NAME + "|" + DATABASE_USER + "|" + DATABASE_PASSWORD);
        console.addIn("insert|" + TABLE_FOR_MANIPULATION + "|column1|value1|column2|value2");
        console.addIn("insert|" + TABLE_FOR_MANIPULATION + "|column1|value3|column2|value4");
        console.addIn("show|" + TABLE_FOR_MANIPULATION + "|column1");
        console.addIn("update|" + TABLE_FOR_MANIPULATION + "|column1|value1|column2|changed");
        console.addIn("show|" + TABLE_FOR_MANIPULATION + "|column1");
        console.addIn("delete|" + TABLE_FOR_MANIPULATION + "|column1|value3");
        console.addIn("show|" + TABLE_FOR_MANIPULATION + "|column1");
        console.addIn("clear|" + TABLE_FOR_MANIPULATION);
        console.addIn("show|" + TABLE_FOR_MANIPULATION + "|column1");
        console.addIn("exit");
        // when
        Main.main(new String[0]);

        // then
        assertEqualsWithConsoleOut("Подключение к базе данных postgres с пользователем postgres прошло успешно\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Запись в таблицу qikmdbrtbloparwecq успешно добавлена. Добавлено строк: 1.\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Запись в таблицу qikmdbrtbloparwecq успешно добавлена. Добавлено строк: 1.\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "┌─────────┬─────────┐\n" +
                "│ column1 │ column2 │\n" +
                "├─────────┼─────────┤\n" +
                "│ value1  │ value2  │\n" +
                "│ value3  │ value4  │\n" +
                "└─────────┴─────────┘\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Количество обновленных записей в таблице qikmdbrtbloparwecq: 1\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "┌─────────┬─────────┐\n" +
                "│ column1 │ column2 │\n" +
                "├─────────┼─────────┤\n" +
                "│ value1  │ changed │\n" +
                "│ value3  │ value4  │\n" +
                "└─────────┴─────────┘\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Количество удаленных записей в таблице qikmdbrtbloparwecq: 1\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "┌─────────┬─────────┐\n" +
                "│ column1 │ column2 │\n" +
                "├─────────┼─────────┤\n" +
                "│ value1  │ changed │\n" +
                "└─────────┴─────────┘\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "Таблица qikmdbrtbloparwecq успешно очищена. Удалено строк: 1.\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n" +
                "┌─────────┬─────────┐\n" +
                "│ column1 │ column2 │\n" +
                "├─────────┼─────────┤\n" +
                "└─────────┴─────────┘\n" +
                "------------------------------------------------------------------\n" +
                "Введите нужную комманду или help для справки (или exit для выхода):\n");
    }

}
