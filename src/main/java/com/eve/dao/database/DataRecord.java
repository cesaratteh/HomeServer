package com.eve.dao.database;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DataRecord {
    private String id;
    private String data;

    public DataRecord() {
    }

    public DataRecord(String id) {
        this.id = id;
    }

    public DataRecord(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String data() throws JsonProcessingException {
        if (this.data == null) this.data = pullData();

        return this.data;
    }

    public String pullData() throws JsonProcessingException {
        throw new RuntimeException("Must be overriden");
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
