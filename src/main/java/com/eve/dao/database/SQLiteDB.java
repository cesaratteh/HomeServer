package com.eve.dao.database;

import com.eve.config.AppConfig;
import com.eve.util.Logger;

import java.sql.*;

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

            Logger.log("Initialized SQLiteDB successfully");
        } catch (Exception e) {
            Logger.error("DaoImpl constructor failed", e);
        }
    }

    @Override
    public void insert(DataRecord dataRecord) throws SQLException {
        String insertQuery = "INSERT INTO " + tableName + " (id, data) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, dataRecord.id());
            preparedStatement.setString(2, dataRecord.data());

            preparedStatement.executeUpdate();
            Logger.log("SQLiteDB insert successfully added " + dataRecord);
        }
    }

    @Override
    public DataRecord get(String id) throws SQLException {
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

        Logger.log("SQLiteDB returning " + (dataRecord != null? dataRecord.data() : "empty"));
        return dataRecord;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
    }
}
