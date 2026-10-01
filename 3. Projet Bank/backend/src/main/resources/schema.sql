CREATE TABLE IF NOT EXISTS employee (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    employee_role ENUM('ADMIN', 'EMPLOYEE') NOT NULL,
    status ENUM('ACTIVE', 'DISABLED') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_at DATETIME NULL
);

INSERT INTO employee (first_name, last_name, email, password_hash, employee_role, status)
VALUES (
    'admin',
    'admin',
    'admin@admin.com',
    '$2a$10$nGZuOMylGJBEb7UGmyG5tuMVvhaoIcThp7wiXN2P2OnXhINM1zoJi',
    'ADMIN',
    'ACTIVE'
)
ON DUPLICATE KEY UPDATE email = VALUES(email);
