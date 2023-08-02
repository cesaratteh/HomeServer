# Home Server Docs

## To create JAR
```
mvn deploy -P release
```

## Running the JAR
```
java -jar file.jar
```

## Running in intellij
Compile using maven, then run

## Accessing tools
* Grafana [http://localhost:3001](http://localhost:3001)
* Prometheus [http://localhost:9090](http://localhost:9090)
* QBitTorrent [http://localhost:8095](http://localhost:8095)
* JellyFin [http://localhost:8096](http://localhost:8096)

## Handler Docs
* BizBuySell Handler [docs::BIZ_BUY_SELL.md](docs/BIZ_BUY_SELL.md)

## Setting up a new device
* Install Chocolatey - `https://chocolatey.org/install`
* Install JDK19 - `https://www.oracle.com/java/technologies/downloads/`
* Open Admin PowerShell
  * Install maven - `choco install maven`
  * Install chrome `choco install googlechrome`
  * Install docker - `choco install docker-desktop`
  * Install JellyFin - `choco install jellyfin`
    * Go to services > JellyFin > Properties > Log On
    * Set that to `Local System`
    * Go through Setup Wizard
      * Set media directories
      * Set directory to the FFmpeg executable (might not be needed)
  * Install qBittorrent - `choco install qbittorrent`
    * Configure to start on startup
    * Configure download folder to `~/Desktop/Environments/HomeServer/media/downloads`
    * enable webUI, set password, and port to `8095`