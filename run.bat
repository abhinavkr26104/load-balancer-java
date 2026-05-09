@echo off
echo Compiling Load Balancer System...
echo.

cd backend\src\main\java

javac -d ..\..\..\..\bin com\loadbalancer\model\*.java
javac -d ..\..\..\..\bin -cp ..\..\..\..\bin com\loadbalancer\service\*.java
javac -d ..\..\..\..\bin -cp ..\..\..\..\bin com\loadbalancer\socket\*.java
javac -d ..\..\..\..\bin -cp ..\..\..\..\bin com\loadbalancer\api\*.java
javac -d ..\..\..\..\bin -cp ..\..\..\..\bin com\loadbalancer\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo Starting Load Balancer System...
echo.

cd ..\..\..\..\

java -cp bin com.loadbalancer.StandaloneApp

pause
