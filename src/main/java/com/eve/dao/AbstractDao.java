package com.eve.dao;

import com.eve.dao.database.DataRecord;
import com.eve.dao.database.SQLiteDB;
import com.eve.util.Logger;

public class AbstractDao implements Dao {

    private final SQLiteDB sqLiteDB;

    public AbstractDao(SQLiteDB sqLiteDB) {
        this.sqLiteDB = sqLiteDB;
    }

    @Override
    public void put(BizBuySellDao.BizBuySellListing listing) {
        if(isPresent(listing.id())) {
            update(listing);
        } else {
            insert(listing);
        }
    }

    public BizBuySellDao.BizBuySellListing get(String id) {
        try {
            DataRecord dataRecord = sqLiteDB.get(id);
            if (dataRecord != null) {
                return BizBuySellDao.BizBuySellListing.fromDataRecord(dataRecord);
            }
        } catch (Exception e) {
            Logger.error("BizBuySellDao get failed with exception ", e);
        }

        return null;
    }

    public void update(BizBuySellDao.BizBuySellListing listing) {
        try {
            sqLiteDB.update(listing.toDataRecord());
        } catch (Exception e) {
            Logger.error("BizBuySellDao put failed with exception ", e);
        }
    }

    @Override
    public void insert(BizBuySellDao.BizBuySellListing listing) {
        try {
            sqLiteDB.insert(listing.toDataRecord());
        } catch (Exception e) {
            Logger.error("BizBuySellDao put failed with exception ", e);
        }
    }

    public boolean isPresent(String id) {
        return get(id) != null;
    }
}
