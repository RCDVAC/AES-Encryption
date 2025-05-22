@echo off

dir /s /B src\*.java > sources.txt

javac -d out @sources.txt

jar cvfm bin/AES.jar manifest.txt -C out .

