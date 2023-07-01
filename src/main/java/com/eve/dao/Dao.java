package com.eve.dao;

public interface Dao {
    public void put(BizBuySellDao.BizBuySellListing listing);

    public BizBuySellDao.BizBuySellListing get(String id);

    public boolean isPresent(String id);
}
