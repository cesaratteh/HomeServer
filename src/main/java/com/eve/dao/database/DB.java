package com.eve.dao.database;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.List;

public interface DB {
    public void insert(DataRecord dataRecord) throws SQLException, JsonProcessingException;

    public void update(DataRecord dataRecord) throws SQLException, JsonProcessingException;

    public DataRecord get(String id) throws SQLException, JsonProcessingException;

    public List<String> getAllIds() throws SQLException;
}
