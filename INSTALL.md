# How to Run

## Prerequisites

1. Maven
2. Java 17 (Temurin Distribution)
3. Ubuntu 20.04

## How to Compile

- Clone Repository

`git clone git@github.com:arsaizdihar/IF2210_TB2_MZC.git`

- Install openjfx

`sudo apt install openjfx`

- Compile package using maven in project root folder

`mvn package`

- Inside app/target folder run

`java --module-path /usr/share/openjfx/lib --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -jar app-1.0-SNAPSHOT-shaded.jar`