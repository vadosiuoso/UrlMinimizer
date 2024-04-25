CREATE TABLE IF NOT EXISTS users
(
  user_id  SERIAL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NOT NULL,
  email    VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255)        NOT NULL,
  is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS links
(
  link_id       SERIAL PRIMARY KEY,
  user_id       INT          NOT NULL,
  original_url  TEXT         NOT NULL,
  short_url     VARCHAR(255) NOT NULL,
  calls         INT         DEFAULT 0,
  creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);
