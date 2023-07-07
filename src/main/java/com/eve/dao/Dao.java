package com.eve.dao;

import com.eve.dao.database.DataRecord;

import java.util.List;

public interface Dao<T extends DataRecord> {
    public void insert(T listing);

    public void put(T listing);

    public void update(T listing);

    public T get(String id);

    public List<String> getAllIds();

    public boolean isPresent(String id);
}
