package model;

import org.junit.Before;
import org.junit.Test;
import ua.moses.sqlcmd.model.DataBaseManager;

import static org.junit.Assert.assertTrue;

public abstract class DataBaseManagerTest {
    private DataBaseManager database;

    @Before
    public void setup(){
        database = getDataBaseManager();
        database.connect("postgres", "postgres", "tend");
    }

    abstract DataBaseManager getDataBaseManager();

    @Test
    public void testIsConnected(){
        //given
        //when
        //then
        assertTrue(database.isConnected());
    }

}
