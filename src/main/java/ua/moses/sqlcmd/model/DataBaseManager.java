package ua.moses.sqlcmd.model;

public interface DataBaseManager {
    void connect (String database, String userName, String password) throws Exception;
    boolean isConnected();
    String[] getTables() throws Exception;
    void createTable(String tableName, String[] columnsName) throws Exception;
    void dropTable(String tableName) throws Exception;
    void clearTable(String tableName) throws Exception;
    String[] getTableData(String tableName) throws Exception;
    void insertRecord (String tableName, String[] columns, String[] values) throws Exception;
    void updateRecord (String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue);
    void deleteRecord (String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue);

}
