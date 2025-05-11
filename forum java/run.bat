@echo off
setlocal

echo 🔧 Compilation des fichiers Java...

set "SRC_DIR=src"
set "BIN_DIR=bin"
set "LIB_JAR=lib\sqlite-jdbc-3.49.1.0.jar"  REM Vérifie ce chemin

REM Crée le dossier bin s'il n'existe pas
if not exist %BIN_DIR% mkdir %BIN_DIR%

REM Compilation des fichiers
for /r %SRC_DIR% %%f in (*.java) do (
    echo Compilation de %%f
    javac -cp %LIB_JAR% -d %BIN_DIR% %%f
)

if errorlevel 1 (
    echo ❌ Erreur de compilation.
    exit /b 1
)

echo ✅ Compilation réussie.
echo 🚀 Lancement de l'application...

REM Assurez-vous d'inclure le JAR SQLite dans le classpath
java -cp %LIB_JAR%;%BIN_DIR% Main

endlocal
pause