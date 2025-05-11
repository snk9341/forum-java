@echo off
setlocal

echo 🔧 Compilation des fichiers Java...

rem Définir les chemins relatifs
set SRC_DIR=src
set BIN_DIR=bin
set LIB_JAR=lib\sqlite-jdbc-3.49.1.0.jar

rem Créer le dossier bin s'il n'existe pas
if not exist %BIN_DIR% (
    mkdir %BIN_DIR%
)

rem Compiler les fichiers Java
javac -cp ".;%LIB_JAR%" -d %BIN_DIR% ^
%SRC_DIR%\ChatWindow.java ^
%SRC_DIR%\Database.java ^
%SRC_DIR%\Forum.java ^
%SRC_DIR%\Main.java ^
%SRC_DIR%\Message.java ^
%SRC_DIR%\User.java

if errorlevel 1 (
    echo ❌ Erreur de compilation.
    pause
    exit /b
)

echo ✅ Compilation réussie.
echo 🚀 Lancement de l'application...

rem Lancer l'application
java -cp ".;%LIB_JAR%;%BIN_DIR%" Main

pause
