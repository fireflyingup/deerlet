#!/bin/sh

cd .. && mvn clean package

cp deerlet-boot/target/deerlet-boot-jar-with-dependencies.jar package/deerlet-boot.jar
cp deerlet-agent/target/deerlet-agent-jar-with-dependencies.jar package/deerlet-agent.jar
cp deerlet-core/target/deerlet-core-jar-with-dependencies.jar package/deerlet-core.jar
cp demo/target/demo-jar-with-dependencies.jar package/demo.jar
cp deerlet-client/target/deerlet-client-jar-with-dependencies.jar package/deerlet-client.jar

