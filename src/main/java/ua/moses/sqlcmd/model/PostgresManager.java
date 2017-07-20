package ua.moses.sqlcmd.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class PostgresManager implements DataBaseManager {
    private Connection connection;
    private String SERVER_NAME = "localhost";
    private String SERVER_PORT = "5432";

    public PostgresManager() {
        this.connection = null;
        Properties properties = new Properties();
        try (FileInputStream propertiesFile = new FileInputStream(new File("src/main/resources/postgres.properties"))) {
            properties.load(propertiesFile);
            this.SERVER_NAME = properties.getProperty("SERVER_NAME");
            this.SERVER_PORT = properties.getProperty("SERVER_PORT");
        } catch (IOException e) {
            //если нет файла конфигурации, то остаются значения по умолчанию
        }
    }

    public void connect(String database, String userName, String password) throws RuntimeException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не подключен jdbc драйвер");
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://" + SERVER_NAME + ":" + SERVER_PORT + "/"
                    + database, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public Set<String> getTables() throws RuntimeException {
        String sql = "SELECT tablename FROM pg_catalog.pg_tables where schemaname = 'public'";
        Set<String> result = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public boolean createTable(String tableName, String[] columnsName) throws RuntimeException {
        String columnsQuery = "";
        for (int i = 0; i < columnsName.length; i++) {
            columnsQuery += columnsName[i] + " text";
            if (i < columnsName.length - 1) {
                columnsQuery += ",\n";
            }
        }
        String sql = "CREATE TABLE public." + tableName + "\n" +
                "(" + columnsQuery + ")\n" +
                "TABLESPACE pg_default";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return true;
    }


    public boolean dropTable(String tableName) throws RuntimeException {
        String sql = "DROP TABLE public." + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    public int clearTable(String tableName) throws RuntimeException {
        String sql = "DELETE FROM public." + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Record> getTableData(String tableName, String sortColumn) throws RuntimeException {
        String sql = "SELECT * FROM public." + tableName;
        if (sortColumn.length() > 0){
            sql+= " ORDER BY " + sortColumn;
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnsCount = metaData.getColumnCount();

            List<Record> result = new LinkedList<>();

            Record columns = new MyRecord(columnsCount);
            for (int i = 0; i < columnsCount; i++) {
                columns.set(i, metaData.getColumnName(i + 1));
            }

            result.add(columns);
            while (resultSet.next()) {
                Record currentRow = new MyRecord(columnsCount);
                for (int i = 0; i < columnsCount; i++) {
                    currentRow.set(i, resultSet.getString(i + 1));
                }
                result.add(currentRow);
            }
            return result;

        } catch (SQLException e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    public int insertRecord(String tableName, String[] columns, String[] values) throws RuntimeException {
        String columnsQuery = "";
        String valuesQuery = "";
        for (int i = 0; i < columns.length; i++) {
            columnsQuery += columns[i];
            if (i < columns.length - 1) {
                columnsQuery += ", ";
            }
        }
        for (int i = 0; i < values.length; i++) {
            valuesQuery += "'" + values[i] + "'";
            if (i < values.length - 1) {
                valuesQuery += ", ";
            }
        }
        String sql = "INSERT INTO public." + tableName + "\n" +
                "(" + columnsQuery + ")\n" +
                "VALUES (" + valuesQuery + ")\n";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int updateRecord(String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue) {
        String sql = "UPDATE public." + tableName + " SET " +
                setColumn + " = '" + setValue + "'  " +
                "WHERE " + criteriaColumn + " = '" + criteriaValue + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int deleteRecord(String tableName, String criteriaColumn, String criteriaValue) {
        String sql = "DELETE FROM public." + tableName +
                " WHERE " + criteriaColumn + " = '" + criteriaValue + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
