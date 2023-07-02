package com.eve.dao;

import com.eve.config.JsonMapper;
import com.eve.dao.database.DataRecord;
import com.eve.dao.database.SQLiteDB;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;

public class BizBuySellDao extends AbstractDao {

    public final static String TABLE_NAME = "BizBuySell";

    public BizBuySellDao(SQLiteDB sqLiteDB) {
        super(sqLiteDB);
    }

    public record BizBuySellListing(
            String id,
            String title,
            String financials,
            String description,
            String detailedInformation,
            String broker,
            String url,
            Date firstSeen,
            Date lastSeen,
            Date firstSeenSold) {

        public static BizBuySellListing fromDataRecord(DataRecord dataRecord) throws JsonProcessingException {
            return JsonMapper.getMapper().readValue(dataRecord.data(), BizBuySellListing.class);
        }

        public DataRecord toDataRecord() throws JsonProcessingException {
            return new DataRecord(id, JsonMapper.getMapper().writeValueAsString(this));
        }
    }
}
