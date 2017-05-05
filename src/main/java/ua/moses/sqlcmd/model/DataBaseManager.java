package ua.moses.sqlcmd.model;

public interface DataBaseManager {
    void connect (String database, String userName, String password) throws RuntimeException;
    boolean isConnected();
    String[] getTables() throws RuntimeException;
    void createTable(String tableName, String[] columnsName) throws RuntimeException;
    void dropTable(String tableName) throws RuntimeException;
    void clearTable(String tableName) throws RuntimeException;
    String[][] getTableData(String tableName) throws RuntimeException;
    void insertRecord (String tableName, String[] columns, String[] values) throws RuntimeException;
    int updateRecord (String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue);
    int deleteRecord (String tableName, String criteriaColumn, String criteriaValue);

}
