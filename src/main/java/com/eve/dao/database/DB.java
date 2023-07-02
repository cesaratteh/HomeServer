package com.eve.dao.database;

import java.sql.SQLException;
import java.util.List;

public interface DB {
    public void insert(DataRecord dataRecord) throws SQLException;

    public void update(DataRecord dataRecord) throws SQLException;

    public DataRecord get(String id) throws SQLException;

    public List<String> getAllIds() throws SQLException;
}
