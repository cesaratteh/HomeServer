package com.eve.dao;

import com.eve.dao.database.SQLiteDB;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class BizBuySellDaoTest {

    @Test
    public void test() throws Exception {
        BizBuySellListing listing =
                new BizBuySellListing(
                        UUID.randomUUID().toString(),
                        "Test",
                        "test",
                        "id",
                        "id",
                        "id",
                        "id",
                        new Date(),
                        new Date(),
                        null
                );

        BizBuySellDao dao = new BizBuySellDao(new SQLiteDB(BizBuySellDao.TABLE_NAME + "testTable_1"));
        BizBuySellDao dao2 = new BizBuySellDao(new SQLiteDB(BizBuySellDao.TABLE_NAME + "testTable_2"));
        dao.put(listing);
        System.out.println(dao.get(listing.getId()));

        dao2.put(listing);
        System.out.println(dao2.get(listing.getId()));
    }
}
