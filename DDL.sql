DROP SCHEMA IF EXISTS spring;
DROP USER IF EXISTS 'user'@'localhost';

CREATE SCHEMA spring;

CREATE USER 'user'@'localhost' IDENTIFIED BY 'pass123';

GRANT ALL PRIVILEGES ON spring.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

USE spring;

CREATE TABLE activity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    activity_code VARCHAR(255) NOT NULL,
    lps VARCHAR(255) NOT NULL,
    UNIQUE (filename),
    UNIQUE (activity_code)
);

CREATE TABLE test_case (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    activityId BIGINT,
    input TEXT,
    expected_output TEXT,
    FOREIGN KEY (activityId) REFERENCES activity(id)
);

CREATE TABLE result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    activityId BIGINT,
    code TEXT,
    FOREIGN KEY (activityId) REFERENCES activity(id)
);

CREATE TABLE result_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    activityId BIGINT,
    result_id BIGINT,
    status VARCHAR(50),
    output TEXT,
    result_time TIMESTAMP,
    FOREIGN KEY (activityId) REFERENCES activity(id),
    FOREIGN KEY (result_id) REFERENCES result(id)
);
