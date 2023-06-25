package com.eve.dao;

public interface Dao {
    public void insert(Snapshot snapshot);

    public Snapshot get(String id);
}
