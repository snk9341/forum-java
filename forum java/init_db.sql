-- Création des tables
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS forums (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    forum_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    timestamp TEXT NOT NULL,
    recipient TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (forum_id) REFERENCES forums(id)
);

-- Données par défaut
INSERT OR IGNORE INTO users (username) VALUES ('Alice'), ('Bob'), ('Charlie');
INSERT OR IGNORE INTO forums (name) VALUES ('Général'), ('Développement'), ('Support');

-- Messages exemples
INSERT INTO messages (user_id, forum_id, content, timestamp, recipient) VALUES
(1, 1, 'Bienvenue sur le forum général !', '2025-05-11 15:00:00', NULL),
(2, 2, '@Alice Peux-tu m''aider avec Java ?', '2025-05-11 15:05:00', 'Alice');
