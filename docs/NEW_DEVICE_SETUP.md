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
    * Install qBittorrent - `choco install qbittorrent`
        * Configure to start on startup
        * Configure download folder to `~/Desktop/Environments/HomeServer/media/downloads`
        * enable webUI, set password, and port to `8095`, allow through firewall
* Setup nginx
    * Generate nginx password file, and copy it to ENV
        * For windows - Download Apache HTTP Server `https://httpd.apache.org/download.cgi`
        * Extract it, and open terminal in the bin directory
        * Run `htpasswd.exe -c htpasswdfile cesar` to generate password file
        * Move `htpasswdfile` to ~/Desktop/Environments/HomeServer/data