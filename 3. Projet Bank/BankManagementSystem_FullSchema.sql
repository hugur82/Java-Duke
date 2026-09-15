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

CREATE TABLE `bank_transaction` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` decimal(18,2) NOT NULL,
  `transaction_type` enum('DEPOSIT','WITHDRAWAL') COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('CREATED','PROCESSING','ACCEPTED','REJECTED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CREATED',
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_transaction_account` (`account_id`),
  CONSTRAINT `fk_transaction_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `chk_transaction_amount` CHECK ((`amount` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
