# Home Server Docs

## To install
```
mvn clean install -P release
```

## Running the app
```
cd ~/Desktop/Environments/HomeServer; mvn exec:java -P release
```

## Single command to pull, build, and run
```
git pull; mvn clean install -P release; cd ~/Desktop/Environments/HomeServer; mvn exec:java -P release
```

## Running in intellij
```
Compile using maven, then run

[Optional]
For nginx, create password file in ./data/<[pass_file>
```

## Accessing tools
* Grafana [http://localhost:3001](http://localhost:3001)
* Prometheus [http://localhost:9090](http://localhost:9090)
* QBitTorrent [http://localhost:8095](http://localhost:8095)
* JellyFin [http://localhost:8096](http://localhost:8096)

## Handler Docs
* BizBuySell Handler [docs::BIZ_BUY_SELL.md](docs/BIZ_BUY_SELL.md)

## Setting up a new device
* [docs::NEW_DEVICE_SETUP.md](docs/NEW_DEVICE_SETUP.md)
