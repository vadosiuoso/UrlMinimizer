--USERS
INSERT INTO users (username, email, password)
VALUES ('Alice', 'alice@example.com', '$2a$10$EMLJmusHhdxHBLY4pk./a.mvCJkyH3oCBvS3hVjZsYLNQtlfkAE1O'),
       ('Bob', 'bob@example.com', '$2a$10$EMLJmusHhdxHBLY4pk./a.mvCJkyH3oCBvS3hVjZsYLNQtlfkAE1O'),
       ('Charlie', 'charlie@example.com', '$2a$10$EMLJmusHhdxHBLY4pk./a.mvCJkyH3oCBvS3hVjZsYLNQtlfkAE1O');

-- URLS
INSERT INTO links (user_id, original_url, short_url)
VALUES (1, 'https://www.verylongwebsiteurl.com/articles/interesting-article', 'abc123'),
       (1, 'https://www.anotherlongurl.com/about-us', 'def456'),
       (2, 'https://www.somesite.com/product/item12345', 'ghi789'),
       (3, 'https://www.example.com/promo', 'jkl012');

--ROLES
INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');


INSERT INTO users_roles (user_id, role_id)
VALUES
(1,1),
(2,1),
(3,2);
