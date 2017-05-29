package ua.moses.sqlcmd.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

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

    public String[] getTables() throws RuntimeException {
        String sql = "SELECT tablename FROM pg_catalog.pg_tables where schemaname = 'public'";
        String[] result = new String[1000];
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            int index = 0;
            while (resultSet.next()) {
                result[index] = resultSet.getString(1);
                index++;
            }
            return Arrays.copyOf(result, index);

        } catch (SQLException e) {

            throw new RuntimeException(e.getMessage());
        }

    }

    public boolean createTable(String tableName, String[] columnsName) throws RuntimeException {
        String columnsQuerry = "";
        for (int i = 0; i < columnsName.length; i++) {
            columnsQuerry += columnsName[i] + " text";
            if (i < columnsName.length - 1) {
                columnsQuerry += ",\n";
            }
        }
        String sql = "CREATE TABLE public." + tableName + "\n" +
                "(" + columnsQuerry + ")\n" +
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

    public String[][] getTableData(String tableName, String sortColumn) throws RuntimeException {
        String sql = "SELECT * FROM public." + tableName;
        if (sortColumn.length() > 0){
            sql+= " ORDER BY " + sortColumn;
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnsCount = metaData.getColumnCount();

            String[][] result = new String[1000][columnsCount];

            String[] columns = new String[columnsCount];
            for (int i = 0; i < columnsCount; i++) {
                columns[i] = metaData.getColumnName(i + 1);
            }

            result[0] = columns;

            int index = 1;
            while (resultSet.next()) {
                String[] currentRow = new String[columnsCount];
                for (int i = 0; i < columnsCount; i++) {
                    currentRow[i] = resultSet.getString(i + 1);
                }
                result[index] = currentRow;
                index++;
            }
            return Arrays.copyOf(result, index);

        } catch (SQLException e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    public int insertRecord(String tableName, String[] columns, String[] values) throws RuntimeException {
        String columnsQuerry = "";
        String valuesQuerry = "";
        for (int i = 0; i < columns.length; i++) {
            columnsQuerry += columns[i];
            if (i < columns.length - 1) {
                columnsQuerry += ", ";
            }
        }
        for (int i = 0; i < values.length; i++) {
            valuesQuerry += "'" + values[i] + "'";
            if (i < values.length - 1) {
                valuesQuerry += ", ";
            }
        }
        String sql = "INSERT INTO public." + tableName + "\n" +
                "(" + columnsQuerry + ")\n" +
                "VALUES (" + valuesQuerry + ")\n";

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
