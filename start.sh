#!/bin/bash
screen -X -S arduino_spring quit
screen -S arduino_spring -d -m ./gradlew run
