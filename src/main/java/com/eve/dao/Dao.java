package com.eve.dao;

import java.util.List;

public interface Dao {
    public void insert(BizBuySellDao.BizBuySellListing listing);

    public void put(BizBuySellDao.BizBuySellListing listing);

    public void update(BizBuySellDao.BizBuySellListing listing);

    public BizBuySellDao.BizBuySellListing get(String id);

    public List<String> getAllIds();

    public boolean isPresent(String id);
}
