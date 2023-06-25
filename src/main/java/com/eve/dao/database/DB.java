package com.eve.dao.database;

import java.sql.SQLException;

public interface DB {
    public void insert(DataRecord dataRecord) throws SQLException;

    public DataRecord get(String id) throws SQLException;
}
