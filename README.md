# HomeService

## To create JAR 
mvn deploy -P release

## Running the JAR
java -jar file.jar

## Running in intellij
Compile using maven, then run

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
  * 