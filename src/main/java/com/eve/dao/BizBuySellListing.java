package com.eve.dao;

import com.eve.config.JsonMapper;
import com.eve.dao.database.DataRecord;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;

public class BizBuySellListing extends DataRecord {
    private String id;
    private String title;
    private String financials;
    private String description;
    private String detailedInformation;
    private String broker;
    private String url;
    private Date firstSeen;
    private Date lastSeen;
    private Date firstSeenSold;

    public BizBuySellListing() {
        super();
    }

    public BizBuySellListing(
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
        super(id);
        this.id = id;
        this.title = title;
        this.financials = financials;
        this.description = description;
        this.detailedInformation = detailedInformation;
        this.broker = broker;
        this.url = url;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
        this.firstSeenSold = firstSeenSold;
    }

    @Override
    public String pullData() throws JsonProcessingException {
        return JsonMapper.getMapper().writeValueAsString(this);
    }

    public String getTitle() {
        return title;
    }

    public String getFinancials() {
        return financials;
    }

    public String getDescription() {
        return description;
    }

    public String getDetailedInformation() {
        return detailedInformation;
    }

    public String getBroker() {
        return broker;
    }

    public String getUrl() {
        return url;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public Date getFirstSeenSold() {
        return firstSeenSold;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BizBuySellListing{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", financials='" + financials + '\'' +
                ", description='" + description + '\'' +
                ", detailedInformation='" + detailedInformation + '\'' +
                ", broker='" + broker + '\'' +
                ", url='" + url + '\'' +
                ", firstSeen=" + firstSeen +
                ", lastSeen=" + lastSeen +
                ", firstSeenSold=" + firstSeenSold +
                '}';
    }
}
