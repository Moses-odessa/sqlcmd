package ua.moses.sqlcmd.model;

import java.util.List;
import java.util.Set;

public interface DataBaseManager {
    void connect (String database, String userName, String password) throws RuntimeException;
    boolean isConnected();
    Set<String> getTables() throws RuntimeException;
    boolean createTable(String tableName, String[] columnsName) throws RuntimeException;
    boolean dropTable(String tableName) throws RuntimeException;
    int clearTable(String tableName) throws RuntimeException;
    List<Record> getTableData(String tableName, String sortColumn) throws RuntimeException;
    int insertRecord (String tableName, String[] columns, String[] values) throws RuntimeException;
    int updateRecord (String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue);
    int deleteRecord (String tableName, String criteriaColumn, String criteriaValue);
}
