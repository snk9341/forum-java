#!/bin/bash

PROJECT_DIR="$(pwd)"
SRC_DIR="$PROJECT_DIR/src"
BIN_DIR="$PROJECT_DIR/bin"
SQLITE_JAR="$PROJECT_DIR/lib/sqlite-jdbc-3.49.1.0.jar"

mkdir -p "$BIN_DIR"

echo "🔧 Compilation des fichiers Java..."

# Compilation sécurisée même avec des espaces
find "$SRC_DIR" -name "*.java" -print0 | xargs -0 javac -cp "$SQLITE_JAR" -d "$BIN_DIR"
if [ $? -ne 0 ]; then
  echo "❌ Erreur de compilation."
  exit 1
fi

echo "✅ Compilation réussie."
echo "🚀 Lancement de l'application..."

java -cp "$SQLITE_JAR:$BIN_DIR" Main
