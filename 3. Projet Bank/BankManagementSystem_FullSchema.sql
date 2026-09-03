DROP DATABASE IF EXISTS BankManagementSystem;
CREATE DATABASE BankManagementSystem
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE BankManagementSystem;

-- =========================
-- Table: customer
-- =========================
CREATE TABLE customer (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    phone VARCHAR(30),
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    address VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') NOT NULL DEFAULT 'ACTIVE'
);

-- =========================
-- Table: account
-- =========================
CREATE TABLE account (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    iban CHAR(34) NOT NULL UNIQUE,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    account_type ENUM('CHECKING', 'SAVINGS') NOT NULL,
    balance DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'BLOCKED', 'CLOSED') NOT NULL DEFAULT 'ACTIVE',

    customer_id BIGINT NOT NULL,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(customer_id)
);

-- =========================
-- Table: bank_transaction
-- =========================
CREATE TABLE bank_transaction (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(18,2) NOT NULL,
    transaction_type ENUM('TRANSFER', 'DEPOSIT', 'WITHDRAWAL') NOT NULL,
    description VARCHAR(255),
    status ENUM('PENDING', 'COMPLETED', 'REJECTED') NOT NULL DEFAULT 'PENDING',

    source_account_id BIGINT,
    destination_account_id BIGINT,

    CONSTRAINT fk_transaction_source_account
        FOREIGN KEY (source_account_id)
        REFERENCES account(account_id),

    CONSTRAINT fk_transaction_destination_account
        FOREIGN KEY (destination_account_id)
        REFERENCES account(account_id),

    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0)
);
