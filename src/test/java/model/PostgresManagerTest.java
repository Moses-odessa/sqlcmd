package model;

import ua.moses.sqlcmd.model.DataBaseManager;
import ua.moses.sqlcmd.model.PostgresManager;

public class PostgresManagerTest extends DataBaseManagerTest {
    @Override
    DataBaseManager getDataBaseManager() {
        return new PostgresManager();
    }
}
