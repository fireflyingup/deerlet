@echo off

echo === Starting Maven Build ===
cd ..
call mvn clean package
if %ERRORLEVEL% neq 0 (
    echo Maven build failed. Exiting...
    pause
    exit /b
)

echo === Creating package directory ===
mkdir package

echo === Copying files to package directory ===
echo f | xcopy /Y /Q deerlet-boot\target\deerlet-boot-jar-with-dependencies.jar package\deerlet-boot.jar
echo f | xcopy /Y /Q deerlet-agent\target\deerlet-agent-jar-with-dependencies.jar package\deerlet-agent.jar
echo f | xcopy /Y /Q deerlet-core\target\deerlet-core-jar-with-dependencies.jar package\deerlet-core.jar
echo f | xcopy /Y /Q demo\target\demo-jar-with-dependencies.jar package\demo.jar
echo f | xcopy /Y /Q deerlet-client\target\deerlet-client-jar-with-dependencies.jar package\deerlet-client.jar

echo === Build and copy completed ===
