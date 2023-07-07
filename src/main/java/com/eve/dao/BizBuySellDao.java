package com.eve.dao;

import com.eve.config.JsonMapper;
import com.eve.dao.database.DataRecord;
import com.eve.dao.database.SQLiteDB;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BizBuySellDao extends AbstractDao<BizBuySellListing> {

    public final static String TABLE_NAME = "BizBuySell";

    public BizBuySellDao(SQLiteDB sqLiteDB) {
        super(sqLiteDB);
    }

    @Override
    public BizBuySellListing fromDataRecord(DataRecord dataRecord) throws JsonProcessingException {
        return JsonMapper.getMapper().readValue(dataRecord.data(), BizBuySellListing.class);
    }

    @Override
    public DataRecord toDataRecord(BizBuySellListing listing) throws JsonProcessingException {
        return new DataRecord(listing.getId(), JsonMapper.getMapper().writeValueAsString(listing));
    }
}
