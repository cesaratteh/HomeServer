package com.eve.dao;

import com.eve.config.Logger;
import com.eve.dao.database.DataRecord;
import com.eve.dao.database.SQLiteDB;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class AbstractDao<T extends DataRecord> implements Dao<T> {

    private final static Logger logger = Logger.getLogger(AbstractDao.class);

    private final SQLiteDB sqLiteDB;

    public AbstractDao(SQLiteDB sqLiteDB) {
        this.sqLiteDB = sqLiteDB;
    }

    @Override
    public void put(T listing) {
        if (isPresent(listing.getId())) {
            update(listing);
        } else {
            insert(listing);
        }
    }

    @Override
    public T get(String id) {
        try {
            DataRecord dataRecord = sqLiteDB.get(id);
            if (dataRecord != null) {
                return fromDataRecord(dataRecord);
            }
        } catch (Exception e) {
            logger.error("BizBuySellDao get failed with exception ", e);
        }

        return null;
    }

    @Override
    public List<String> getAllIds() {
        List<String> ids = new ArrayList<>();

        try {
            ids = sqLiteDB.getAllIds();
        } catch (Exception e) {
            logger.error("BizBuySellDao getAllIds failed with exception ", e);
        }

        return ids;
    }

    @Override
    public void update(T listing) {
        try {
            sqLiteDB.update(toDataRecord(listing));
        } catch (Exception e) {
            logger.error("BizBuySellDao put failed with exception ", e);
        }
    }

    @Override
    public void insert(T listing) {
        try {
            sqLiteDB.insert(toDataRecord(listing));
        } catch (Exception e) {
            logger.error("BizBuySellDao put failed with exception ", e);
        }
    }

    @Override
    public boolean isPresent(String id) {
        return get(id) != null;
    }

    public T fromDataRecord(DataRecord dataRecord) throws JsonProcessingException {
        throw new RuntimeException("Method must be overriden");
    }

    public DataRecord toDataRecord(T object) throws JsonProcessingException {
        throw new RuntimeException("Method must be overriden");
    }
}
