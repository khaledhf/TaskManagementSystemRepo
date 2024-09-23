CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Task (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      status ENUM('STARTED', 'IN_PROGRESS', 'DONE', 'PENDING', 'CANCELED') NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ChangeLog (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           task_id BIGINT NOT NULL,
                           status_from ENUM('STARTED', 'IN_PROGRESS', 'DONE', 'PENDING', 'CANCELED') NOT NULL,
                           status_to ENUM('STARTED', 'IN_PROGRESS', 'DONE', 'PENDING', 'CANCELED') NOT NULL,
                           changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (task_id) REFERENCES Task(id) ON DELETE CASCADE
);
