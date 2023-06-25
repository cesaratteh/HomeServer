package com.eve.dao;

import com.eve.dao.database.SQLiteDB;
import com.eve.util.Logger;

public class AbstractDao implements Dao {

    private final SQLiteDB sqLiteDB;

    public AbstractDao(SQLiteDB sqLiteDB) {
        this.sqLiteDB = sqLiteDB;
    }

    public void put(BizBuySellDao.BizBuySellListing listing) {
        try {
            sqLiteDB.insert(listing.toDataRecord());
        } catch (Exception e) {
            Logger.error("BizBuySellDao put failed with exception ", e);
        }
    }

    public BizBuySellDao.BizBuySellListing get(String id) {
        try {
            return BizBuySellDao.BizBuySellListing.fromDataRecord(sqLiteDB.get(id));
        } catch (Exception e) {
            Logger.error("BizBuySellDao get failed with exception ", e);
        }

        return null;
    }
}
