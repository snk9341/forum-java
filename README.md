
# Forum Java

## Prérequis

Avant de commencer l'installation, assurez-vous que les éléments suivants sont installés sur votre machine :

### 1. Java Development Kit (JDK)

Le projet nécessite Java pour être compilé et exécuté. Vous devez installer le JDK.

#### **Linux**
- Pour installer Java (OpenJDK 11 ou supérieur) :
  ```bash
  sudo apt update
  sudo apt install openjdk-11-jdk
  ```

#### **Windows**
- Téléchargez et installez la dernière version du [JDK sur le site officiel d'Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- N'oubliez pas de configurer la variable d'environnement `JAVA_HOME`.

### 2. SQLite JDBC Driver

Le projet utilise **SQLite** pour la gestion de la base de données. Le driver **JDBC SQLite** est nécessaire pour établir la connexion avec la base de données.

- **Téléchargez le fichier JAR du driver SQLite JDBC** depuis le site officiel :  
  [SQLite JDBC Driver](https://bitbucket.org/xerial/sqlite-jdbc/downloads/)

- **Nom du fichier** : `sqlite-jdbc-3.49.1.0.jar`

- **Placez le fichier JAR dans un dossier `lib/`** à la racine du projet.

---

## Installation du projet

### 1. Télécharger ou placer les fichiers

#### Linux / Windows

Vous devez copier tous les fichiers du projet dans un dossier de votre choix sur votre machine. Assurez-vous que la structure du projet est la suivante :

```
/forum-java
    ├── /bin
    ├── /lib
        └── sqlite-jdbc-3.49.1.0.jar
    ├── /src
        ├── Database.java
        ├── ChatWindow.java
        ├── Forum.java
        ├── Main.java
        ├── Message.java
        └── User.java
    ├── /resources
        └── chat_forum.db (base de données SQLite)
    └── run.sh (pour Linux)
    └── run.bat (pour Windows)
```

### 2. Créer la base de données SQLite

1. Créez un fichier SQLite vide appelé `chat_forum.db` dans le dossier `/resources`.
2. Si vous n'avez pas la structure de la base de données, utilisez ce script SQL pour créer les tables dans SQLite :

```sql
-- Création des tables
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL
);

CREATE TABLE forums (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    forum_id INTEGER,
    message TEXT NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (forum_id) REFERENCES forums(id)
);

-- Exemple d'ajout d'un utilisateur
INSERT INTO users (username) VALUES ('Alice');
INSERT INTO users (username) VALUES ('Bob');

-- Exemple d'ajout d'un forum
INSERT INTO forums (name) VALUES ('Java Discussion');
INSERT INTO forums (name) VALUES ('Tech News');
```

Vous pouvez utiliser un outil comme [DB Browser for SQLite](https://sqlitebrowser.org/) pour exécuter ce script et créer la base de données.

---

## Compilation et exécution du projet

### Sur Linux

#### 1. Compiler le projet

Ouvrez un terminal et naviguez jusqu'au dossier du projet. Puis exécutez la commande suivante pour compiler tous les fichiers Java en utilisant le JDK :

```bash
javac -cp ".:lib/sqlite-jdbc-3.49.1.0.jar" -d bin src/*.java
```

#### 2. Exécuter l'application

Après la compilation, vous pouvez exécuter l'application avec la commande suivante :

```bash
java -cp "bin:lib/sqlite-jdbc-3.49.1.0.jar" Main
```

#### 3. Lancer avec le script `run.sh`

Si vous avez le fichier `run.sh`, vous pouvez l'utiliser pour automatiser la compilation et l'exécution.

1. Assurez-vous que `run.sh` est exécutable :
   ```bash
   chmod +x run.sh
   ```

2. Exécutez le script :
   ```bash
   ./run.sh
   ```

### Sur Windows

#### 1. Compiler le projet

1. Ouvrez **l'invite de commandes** (cmd).
2. Accédez au dossier du projet.
3. Compilez le projet en utilisant la commande suivante :
   ```cmd
   javac -cp ".;lib\sqlite-jdbc-3.49.1.0.jar" -d bin src\*.java
   ```

#### 2. Exécuter l'application

1. Toujours dans l'invite de commandes, exécutez le programme avec cette commande :
   ```cmd
   java -cp "bin;lib\sqlite-jdbc-3.49.1.0.jar" Main
   ```

#### 3. Lancer avec le fichier `run.bat`

1. Si vous avez un fichier `run.bat`, double-cliquez dessus pour lancer la compilation et l'exécution automatiquement.

---

## Dépannage

### 1. `No suitable driver found for jdbc:sqlite:chat_forum.db`

- **Problème** : Le driver JDBC SQLite n'est pas trouvé.
- **Solution** : Assurez-vous que le fichier `sqlite-jdbc-3.49.1.0.jar` est bien dans le dossier `lib/` et que le chemin est bien indiqué lors de l'exécution avec `-cp`.

### 2. Erreur de compilation (`javac` non trouvé)

- **Problème** : Java n'est pas installé ou le chemin d'accès à `javac` n'est pas correctement configuré.
- **Solution** : Vérifiez que **Java** est bien installé et que la variable d'environnement `JAVA_HOME` est définie.

---

## Conclusion

Ce guide vous aide à configurer et exécuter l'application **Forum Java** sur **Linux** et **Windows**. Assurez-vous d'avoir les bonnes versions des dépendances, notamment **Java** et le **SQLite JDBC driver**. Si vous rencontrez des problèmes lors de l'exécution, vérifiez les chemins d'accès aux fichiers JAR et assurez-vous que la base de données SQLite est correctement configurée.
