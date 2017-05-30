package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.moses.sqlcmd.model.DataBaseManager;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;

public abstract class DataBaseManagerTest {
    private DataBaseManager database;
    private final String TEST_TABLE_NAME = "djdjdkgltunfdteghjklqwyb";
    private final String[] TEST_TABLE_COLUMNS = {"column1", "column2"};
    private final String[] TEST_TABLE_VALUES = {"values1", "values2"};
    private final String[] TEST_TABLE_VALUES_CHANGED = {"values1", "changed"};

    abstract DataBaseManager getDataBaseManager();

    @Before
    public void setup() {
        database = getDataBaseManager();
        database.connect("postgres", "postgres", "tend");
        database.createTable(TEST_TABLE_NAME, TEST_TABLE_COLUMNS);   //создаем тестовую таблицу
    }

    @After
    public void finish() {
        database.dropTable(TEST_TABLE_NAME); //удаляем тестовую таблицу
    }

    @Test
    public void testIsConnected() {
        //given
        //when
        //then
        assertTrue(database.isConnected());
    }

    @Test
    public void testGetTables() {
        //given
        //when
        //then
        assertThat(Arrays.toString(database.getTables()),
                containsString(TEST_TABLE_NAME));
    }

    @Test
    public void testInsertRecord() {
        //given
        database.clearTable(TEST_TABLE_NAME);
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);
        //then
        assertEquals(Arrays.toString(TEST_TABLE_VALUES),
                Arrays.toString(database.getTableData(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0])[1]));
    }

    @Test
    public void testDeleteRecord() {
        //given
        database.clearTable(TEST_TABLE_NAME);
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);
        //then
        assertEquals(1,
                database.deleteRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0], TEST_TABLE_VALUES[0]));
    }

    @Test
    public void testDeleteRecordBadCriteria() {
        //given
        database.clearTable(TEST_TABLE_NAME);
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);
        //then
        assertEquals(0,
                database.deleteRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0], TEST_TABLE_VALUES[1]));
    }

    @Test
    public void testUpdateRecord() {
        //given
        database.clearTable(TEST_TABLE_NAME);
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);

        //then
        assertEquals(1,
                database.updateRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0], TEST_TABLE_VALUES[0], TEST_TABLE_COLUMNS[1], TEST_TABLE_VALUES_CHANGED[1]));
    }

    @Test
    public void testUpdateRecordBadCriteria() {
        //given
        database.clearTable(TEST_TABLE_NAME);
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);

        //then
        assertEquals(0,
                database.updateRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0], TEST_TABLE_VALUES[1], TEST_TABLE_COLUMNS[1], TEST_TABLE_VALUES_CHANGED[1]));
    }

    @Test
    public void testClearTable() {
        //given
        //when
        database.insertRecord(TEST_TABLE_NAME, TEST_TABLE_COLUMNS, TEST_TABLE_VALUES);
        database.clearTable(TEST_TABLE_NAME);
        //then
        assertEquals(1,
                database.getTableData(TEST_TABLE_NAME, TEST_TABLE_COLUMNS[0]).length);
    }


}
