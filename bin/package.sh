#!/bin/sh

cd .. && mvn clean package

cp boot/target/boot-jar-with-dependencies.jar package/boot.jar
cp agent/target/agent-jar-with-dependencies.jar package/agent.jar
cp core/target/core-jar-with-dependencies.jar package/core.jar

