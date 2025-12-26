CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE complaints (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    complaint VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO users (username, password, role)
VALUES
(
  'user',
  '$2a$10$ktQ8620O4bB4OV3i8w7YNeC/VH5ipoYd.05KDvev5DkyvWIjG/g7G',
  'USER'
),
(
  'admin',
  '$2a$10$9URBjQqnrlt6SCKZchq0KecIzRgtaWCnDskbQKwjw4Qemq7/ccmEy',
  'ADMIN'
);

