# BizBuySell Handler

## Extracting info from the BizBuySellDB
```
SELECT
json_extract(data ,"$.id") as id,
json_extract(data ,"$.title") as title,
json_extract(data ,"$.financials") as financials,
json_extract(data ,"$.description") as description,
json_extract(data ,"$.detailedInformation") as detailedInformation,
json_extract(data ,"$.broker") as broker,
json_extract(data ,"$.url") as url,
json_extract(data ,"$.firstSeen") as firstSeen,
json_extract(data ,"$.lastSeen") as lastSeen,
json_extract(data ,"$.firstSeenSold") as firstSeenSold,
replace(replace(substr(json_extract(data ,"$.financials"),
        instr(json_extract(data ,"$.financials"), "Asking Price:") + 13,
        instr(json_extract(data ,"$.financials"), "Cash Flow:") - 13 - instr(json_extract(data ,"$.financials"), "Asking Price:")), CHAR(10), ""), "N/A", "$0")
        as price,
replace(replace(substr(json_extract(data ,"$.financials"),
        instr(json_extract(data ,"$.financials"), "Cash Flow:") + 10,
        instr(json_extract(data ,"$.financials"), "Gross Revenue:") - 10 - instr(json_extract(data ,"$.financials"), "Cash Flow:")), CHAR(10), ""), "N/A", "$0")
        as cashFlow
FROM BizBuySell;
```