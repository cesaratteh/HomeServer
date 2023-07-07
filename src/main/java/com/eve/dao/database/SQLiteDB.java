package com.eve.dao.database;

import com.eve.config.AppConfig;
import com.eve.config.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDB implements DB {
    private static final String DB_FILE = AppConfig.SQLITE_DAO_DB_FILE;

    private final String tableName;

    public SQLiteDB(String tableName) {
        this.tableName = tableName;

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String createTableQuery =
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (id TEXT PRIMARY KEY, data TEXT)";
            statement.execute(createTableQuery);

            LoggerFactory.getLogger(this.getClass()).info("Initialized SQLiteDB successfully");
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error("DaoImpl constructor failed", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
    }

    @Override
    public void update(DataRecord dataRecord) throws SQLException, JsonProcessingException {
        String updateQuery = "UPDATE " + tableName + " SET data = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, dataRecord.data());
            preparedStatement.setString(2, dataRecord.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                LoggerFactory.getLogger(this.getClass()).info("SQLiteDB update successfully updated " + dataRecord);
            } else {
                LoggerFactory.getLogger(this.getClass()).info("SQLiteDB update failed, record not found: " + dataRecord);
            }
        }
    }

    @Override
    public void insert(DataRecord dataRecord) throws SQLException, JsonProcessingException {
        String insertQuery = "INSERT INTO " + tableName + " (id, data) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, dataRecord.getId());
            preparedStatement.setString(2, dataRecord.data());

            preparedStatement.executeUpdate();
            LoggerFactory.getLogger(this.getClass()).info("SQLiteDB insert successfully added " + dataRecord);
        }
    }

    @Override
    public DataRecord get(String id) throws SQLException, JsonProcessingException {
        DataRecord dataRecord = null;

        String selectQuery = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String snapshotId = resultSet.getString("id");
                String data = resultSet.getString("data");

                dataRecord = new DataRecord(snapshotId, data);
            }

            resultSet.close();
        }

        LoggerFactory.getLogger(this.getClass()).info("SQLiteDB returning " + (dataRecord != null ? dataRecord.data() : "empty"));
        return dataRecord;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        List<String> ids = new ArrayList<>();

        String selectQuery = "SELECT id FROM " + tableName;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                ids.add(id);
            }
        }

        return ids;
    }
}
