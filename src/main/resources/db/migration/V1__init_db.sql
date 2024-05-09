CREATE TABLE IF NOT EXISTS users
(
  id  SERIAL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NOT NULL,
  email    VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255)        NOT NULL
);

CREATE TABLE IF NOT EXISTS links
(
  id       SERIAL PRIMARY KEY,
  user_id       INT          NOT NULL,
  original_url  TEXT         NOT NULL,
  short_url     VARCHAR(255) NOT NULL,
  calls         INT         DEFAULT 0,
  creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS roles
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
)

