# How to Run

## Prerequisites

1. Maven
2. Java 17 (Temurin Distribution)
3. Ubuntu 20.04

## How to Compile

- Clone Repository

`git clone git@github.com:arsaizdihar/IF2210_TB2_MZC.git`

- Download openjfx 18

Download openjfx from this link
`https://download2.gluonhq.com/openjfx/18.0.2/openjfx-18.0.2_linux-aarch64_bin-sdk.zip`

Extract the zip and remember the extracted path

- Compile package using maven in project root folder

`mvn package`

- Inside app/target folder run

`
java \
--module-path javafx-sdk-18.0.2\lib \
--add-exports=javafx.base/com.sun.javafx.event=org.controlsfx.controls \
-jar app-1.0-SNAPSHOT-shaded.jar
`

replace javafx-sdk-18.0.2\lib with relative or absolute path to the same folder from previously extracted folder