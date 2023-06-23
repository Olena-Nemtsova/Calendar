CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR,
    password VARCHAR,
    email VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS event (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP,
    description VARCHAR,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);
