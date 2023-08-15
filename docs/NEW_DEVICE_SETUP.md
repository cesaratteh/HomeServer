# New Device Setup Steps

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
        * Delete default user, and setup user with password
    * Install qBittorrent - `choco install qbittorrent`
        * Configure to start on startup
        * Configure download folder to `~/Desktop/Environments/HomeServer/media/downloads`
        * enable webUI, set password, and port to `8095`, allow through firewall
* [Optional] Setup Caddy
  * To generate password `docker-compose run caddy caddy hash-password`
* Setup grafana behind caddy
    * Setup username/password that matches caddy