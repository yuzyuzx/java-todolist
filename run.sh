#!/bin/bash
javac -d build src/main/java/*.java
java -cp build Main
