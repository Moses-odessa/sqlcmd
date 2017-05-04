package ua.moses.sqlcmd.model;

import ua.moses.sqlcmd.controller.command.Connect;

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
    private final String DEFAULT_ERROR_MESSAGE = "Ошибка выполнения комманды. ";

    public PostgresManager() {
        this.connection = null;
        Properties properties = new Properties();
        try (FileInputStream propertiesFile = new FileInputStream(new File("src/main/resources/postgres.properties"))) {
            properties.load(propertiesFile);
            this.SERVER_NAME = properties.getProperty("SERVER_NAME");
            this.SERVER_PORT = properties.getProperty("SERVER_PORT");
        } catch (IOException e) {
            //если нет файла конфигурации, то остаются значения по умолчанию
            //System.out.println("Файл конфигурации не найден. Используются значения по умолчанию");
        }
    }

    public void connect(String database, String userName, String password) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Не подключен jdbc драйвер", e);
        }
        try  {
            this.connection = DriverManager.getConnection("jdbc:postgresql://" + SERVER_NAME + ":" + SERVER_PORT + "/"
                + database, userName, password);
        } catch (SQLException e) {
            throw new Exception(DEFAULT_ERROR_MESSAGE + e.getMessage());
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public String[] getTables() throws Exception {
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

            throw new Exception(DEFAULT_ERROR_MESSAGE + e.getMessage());
        }

    }

    public void createTable(String tableName, String[] columnsName) throws Exception {
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
            throw new Exception(DEFAULT_ERROR_MESSAGE + e.getMessage());
        }

    }

    public void dropTable(String tableName) throws Exception {
        String sql = "DROP TABLE public." + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(DEFAULT_ERROR_MESSAGE + e.getMessage());
        }

    }

    public void clearTable(String tableName) throws Exception {
        String sql = "DELETE FROM public." + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(DEFAULT_ERROR_MESSAGE + e.getMessage());
        }
    }

    public String[] getTableData(String tableName) {
        return new String[0];
    }

    public void insertRecord(String tableName, String[] columns, String[] values) {

    }

    public void updateRecord(String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue) {

    }

    public void deleteRecord(String tableName, String criteriaColumn, String criteriaValue, String setColumn, String setValue) {

    }
}
