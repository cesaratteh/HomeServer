package com.eve.dao;

import com.eve.config.AppConfig;
import com.eve.util.Logger;

import java.sql.*;

public class SQLiteDao implements Dao {
    private static final String DB_FILE = AppConfig.SQLITE_DAO_DB_FILE;

    public SQLiteDao() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String createTableQuery =
                    "CREATE TABLE IF NOT EXISTS snapshots (id TEXT PRIMARY KEY, data TEXT)";
            statement.execute(createTableQuery);
        } catch (Exception e) {
            Logger.error("DaoImpl constructor failed", e);
        }
    }

    @Override
    public void insert(Snapshot snapshot) {
        String insertQuery = "INSERT INTO snapshots (id, data) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, snapshot.id());
            preparedStatement.setString(2, snapshot.data());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Logger.error("DaoImpl insert failed", e);
        }
    }

    @Override
    public Snapshot get(String id) {
        Snapshot snapshot = null;

        String selectQuery = "SELECT * FROM snapshots WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String snapshotId = resultSet.getString("id");
                String data = resultSet.getString("data");

                snapshot = new Snapshot(snapshotId, data);
            }

            resultSet.close();
        } catch (Exception e) {
            Logger.error("DaoImpl get failed", e);
        }

        return snapshot;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
    }
}
