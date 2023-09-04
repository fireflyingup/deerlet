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
echo f | xcopy /Y /Q boot\target\boot-jar-with-dependencies.jar package\boot.jar
echo f | xcopy /Y /Q agent\target\agent-jar-with-dependencies.jar package\agent.jar
echo f | xcopy /Y /Q core\target\core-jar-with-dependencies.jar package\core.jar
echo f | xcopy /Y /Q demo\target\demo-jar-with-dependencies.jar package\demo.jar
echo f | xcopy /Y /Q client\target\client-jar-with-dependencies.jar package\client.jar

echo === Build and copy completed ===
