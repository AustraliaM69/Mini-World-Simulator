@echo off
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo Error: Java compiler not found or compilation failed.
    echo Please make sure Java is installed and in your PATH.
    echo You can download Java from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo Compilation successful!
echo Running the World Simulator...
java WorldSimApp
pause
