-- Demo data for BankManagementSystem
-- 20 customers
-- Customers 1-10: one checking account each
-- Customers 11-20: multiple accounts
-- Customers 19 and 20 intentionally have no transactions

USE BankManagementSystem;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE bank_transaction;
TRUNCATE TABLE account;
TRUNCATE TABLE customer;
SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- Customers
-- =========================
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Emma', 'Martin', '1981-02-02', '+33 6 10 20 01 01', 'emma.martin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '11 Avenue des Lilas', '67001', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Lucas', 'Bernard', '1982-03-03', '+33 6 10 20 02 02', 'lucas.bernard@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '12 Avenue des Lilas', '67002', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Chloe', 'Robert', '1983-04-04', '+33 6 10 20 03 03', 'chloe.robert@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '13 Avenue des Lilas', '67003', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Thomas', 'Richard', '1984-05-05', '+33 6 10 20 04 04', 'thomas.richard@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '14 Avenue des Lilas', '67004', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Lea', 'Petit', '1985-06-06', '+33 6 10 20 05 05', 'lea.petit@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '15 Avenue des Lilas', '67005', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Hugo', 'Durand', '1986-07-07', '+33 6 10 20 06 06', 'hugo.durand@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '16 Avenue des Lilas', '67006', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Camille', 'Leroy', '1987-08-08', '+33 6 10 20 07 07', 'camille.leroy@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '17 Avenue des Lilas', '67007', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Louis', 'Moreau', '1988-09-09', '+33 6 10 20 08 08', 'louis.moreau@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '18 Avenue des Lilas', '67008', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Manon', 'Simon', '1989-10-10', '+33 6 10 20 09 09', 'manon.simon@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '19 Avenue des Lilas', '67009', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Gabriel', 'Laurent', '1990-11-11', '+33 6 10 20 10 10', 'gabriel.laurent@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '20 Avenue des Lilas', '67010', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Sarah', 'Lefevre', '1991-12-12', '+33 6 10 20 11 11', 'sarah.lefevre@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '21 Avenue des Lilas', '67011', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Antoine', 'Michel', '1992-01-13', '+33 6 10 20 12 12', 'antoine.michel@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '22 Avenue des Lilas', '67012', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Julie', 'Garcia', '1993-02-14', '+33 6 10 20 13 13', 'julie.garcia@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '23 Avenue des Lilas', '67013', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Nathan', 'David', '1994-03-15', '+33 6 10 20 14 14', 'nathan.david@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '24 Avenue des Lilas', '67014', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Sophie', 'Bertrand', '1995-04-16', '+33 6 10 20 15 15', 'sophie.bertrand@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '25 Avenue des Lilas', '67015', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Maxime', 'Roux', '1996-05-17', '+33 6 10 20 16 16', 'maxime.roux@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '26 Avenue des Lilas', '67016', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Laura', 'Vincent', '1980-06-18', '+33 6 10 20 17 17', 'laura.vincent@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '27 Avenue des Lilas', '67017', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Paul', 'Fournier', '1981-07-19', '+33 6 10 20 18 18', 'paul.fournier@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '28 Avenue des Lilas', '67018', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Marion', 'Girard', '1982-08-20', '+33 6 10 20 19 19', 'marion.girard@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '29 Avenue des Lilas', '67019', 'Strasbourg', 'ACTIVE');
INSERT INTO customer (first_name, last_name, date_of_birth, phone, email, password_hash, role, address, postal_code, city, status) VALUES ('Alexandre', 'Bonnet', '1983-09-21', '+33 6 10 20 20 20', 'alexandre.bonnet@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', '30 Avenue des Lilas', '67020', 'Strasbourg', 'ACTIVE');

-- =========================
-- Accounts
-- =========================
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000010037', 'ACCT-000001', 'CHECKING', 0.00, '2026-01-02 09:00:00', 'ACTIVE', 1);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000020074', 'ACCT-000002', 'CHECKING', 0.00, '2026-01-03 09:00:00', 'ACTIVE', 2);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000030111', 'ACCT-000003', 'CHECKING', 0.00, '2026-01-04 09:00:00', 'ACTIVE', 3);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000040148', 'ACCT-000004', 'CHECKING', 0.00, '2026-01-05 09:00:00', 'ACTIVE', 4);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000050185', 'ACCT-000005', 'CHECKING', 0.00, '2026-01-06 09:00:00', 'ACTIVE', 5);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000060222', 'ACCT-000006', 'CHECKING', 0.00, '2026-01-07 09:00:00', 'ACTIVE', 6);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000070259', 'ACCT-000007', 'CHECKING', 0.00, '2026-01-08 09:00:00', 'ACTIVE', 7);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000080296', 'ACCT-000008', 'CHECKING', 0.00, '2026-01-09 09:00:00', 'ACTIVE', 8);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000090333', 'ACCT-000009', 'CHECKING', 0.00, '2026-01-10 09:00:00', 'ACTIVE', 9);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000100370', 'ACCT-000010', 'CHECKING', 0.00, '2026-01-11 09:00:00', 'ACTIVE', 10);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000110407', 'ACCT-000011', 'CHECKING', 0.00, '2026-01-12 09:00:00', 'ACTIVE', 11);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000120444', 'ACCT-000012', 'SAVINGS', 0.00, '2026-01-13 09:00:00', 'ACTIVE', 11);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000130481', 'ACCT-000013', 'CHECKING', 0.00, '2026-01-14 09:00:00', 'ACTIVE', 12);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000140518', 'ACCT-000014', 'SAVINGS', 0.00, '2026-01-15 09:00:00', 'ACTIVE', 12);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000150555', 'ACCT-000015', 'CHECKING', 0.00, '2026-01-16 09:00:00', 'ACTIVE', 12);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000160592', 'ACCT-000016', 'CHECKING', 0.00, '2026-01-17 09:00:00', 'ACTIVE', 13);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000170629', 'ACCT-000017', 'SAVINGS', 0.00, '2026-01-18 09:00:00', 'ACTIVE', 13);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000180666', 'ACCT-000018', 'CHECKING', 0.00, '2026-01-19 09:00:00', 'ACTIVE', 14);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000190703', 'ACCT-000019', 'SAVINGS', 0.00, '2026-01-20 09:00:00', 'ACTIVE', 14);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000200740', 'ACCT-000020', 'CHECKING', 0.00, '2026-01-21 09:00:00', 'ACTIVE', 15);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000210777', 'ACCT-000021', 'SAVINGS', 0.00, '2026-01-22 09:00:00', 'ACTIVE', 15);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000220814', 'ACCT-000022', 'CHECKING', 0.00, '2026-01-23 09:00:00', 'ACTIVE', 15);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000230851', 'ACCT-000023', 'CHECKING', 0.00, '2026-01-24 09:00:00', 'ACTIVE', 16);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000240888', 'ACCT-000024', 'SAVINGS', 0.00, '2026-01-25 09:00:00', 'ACTIVE', 16);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000250925', 'ACCT-000025', 'CHECKING', 0.00, '2026-01-26 09:00:00', 'ACTIVE', 17);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000260962', 'ACCT-000026', 'SAVINGS', 0.00, '2026-01-27 09:00:00', 'ACTIVE', 17);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000270999', 'ACCT-000027', 'CHECKING', 0.00, '2026-01-01 09:00:00', 'ACTIVE', 18);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000281036', 'ACCT-000028', 'SAVINGS', 0.00, '2026-01-02 09:00:00', 'ACTIVE', 18);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000291073', 'ACCT-000029', 'CHECKING', 0.00, '2026-01-03 09:00:00', 'ACTIVE', 18);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000301110', 'ACCT-000030', 'CHECKING', 0.00, '2026-01-04 09:00:00', 'ACTIVE', 19);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000311147', 'ACCT-000031', 'SAVINGS', 0.00, '2026-01-05 09:00:00', 'ACTIVE', 19);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000321184', 'ACCT-000032', 'CHECKING', 0.00, '2026-01-06 09:00:00', 'ACTIVE', 20);
INSERT INTO account (iban, account_number, account_type, balance, creation_date, status, customer_id) VALUES ('FR7630006000010000000000331221', 'ACCT-000033', 'SAVINGS', 0.00, '2026-01-07 09:00:00', 'ACTIVE', 20);

-- =========================
-- Bank transactions
-- =========================
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-01 10:00:00', 2325.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 1, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-02 10:00:00', 662.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 1, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-03 10:00:00', 46.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 1, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-04 10:00:00', 36.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 1, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-05 10:00:00', 2450.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-06 10:00:00', 674.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-07 10:00:00', 47.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-08 10:00:00', 40.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-09 10:00:00', 130.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-10 10:00:00', 57.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 2, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-11 10:00:00', 2575.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 3, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-12 10:00:00', 686.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 3, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-13 10:00:00', 48.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 3, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-14 10:00:00', 44.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 3, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-15 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 3, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-16 10:00:00', 2700.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-17 10:00:00', 698.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-18 10:00:00', 49.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-19 10:00:00', 48.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-20 10:00:00', 140.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-21 10:00:00', 59.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 4, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-22 10:00:00', 2825.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 5, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-23 10:00:00', 710.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 5, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-24 10:00:00', 50.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 5, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-25 10:00:00', 52.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 5, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-26 10:00:00', 2950.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-27 10:00:00', 722.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-02-28 10:00:00', 51.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-01 10:00:00', 56.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-02 10:00:00', 150.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-03 10:00:00', 61.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-04 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 6, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-05 10:00:00', 3075.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 7, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-06 10:00:00', 734.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 7, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-07 10:00:00', 52.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 7, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-08 10:00:00', 32.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 7, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-09 10:00:00', 3200.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-10 10:00:00', 746.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-11 10:00:00', 53.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-12 10:00:00', 36.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-13 10:00:00', 160.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-14 10:00:00', 63.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 8, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-15 10:00:00', 3325.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 9, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-16 10:00:00', 758.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 9, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-17 10:00:00', 54.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 9, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-18 10:00:00', 40.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 9, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-19 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 9, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-20 10:00:00', 3450.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-21 10:00:00', 770.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-22 10:00:00', 55.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-23 10:00:00', 44.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-24 10:00:00', 170.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-25 10:00:00', 65.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 10, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-26 10:00:00', 3575.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 11, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-27 10:00:00', 782.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 11, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-28 10:00:00', 56.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 11, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-29 10:00:00', 48.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 11, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-30 10:00:00', 410.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 11, 12);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-03-31 10:00:00', 3700.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-01 10:00:00', 794.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-02 10:00:00', 57.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-03 10:00:00', 52.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-04 10:00:00', 180.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-05 10:00:00', 67.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-06 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 13, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-07 10:00:00', 420.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 13, 14);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-08 10:00:00', 3825.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 16, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-09 10:00:00', 806.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 16, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-10 10:00:00', 58.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 16, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-11 10:00:00', 56.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 16, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-12 10:00:00', 430.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 16, 17);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-13 10:00:00', 3950.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-14 10:00:00', 818.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-15 10:00:00', 59.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-16 10:00:00', 32.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-17 10:00:00', 190.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-18 10:00:00', 69.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 18, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-19 10:00:00', 440.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 18, 19);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-20 10:00:00', 4075.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 20, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-21 10:00:00', 830.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 20, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-22 10:00:00', 60.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 20, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-23 10:00:00', 36.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 20, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-24 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 20, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-25 10:00:00', 450.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 20, 21);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-26 10:00:00', 4200.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-27 10:00:00', 842.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-28 10:00:00', 61.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-29 10:00:00', 40.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-04-30 10:00:00', 200.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-01 10:00:00', 71.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 23, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-02 10:00:00', 460.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 23, 24);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-03 10:00:00', 4325.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 25, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-04 10:00:00', 854.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 25, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-05 10:00:00', 62.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 25, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-06 10:00:00', 44.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 25, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-07 10:00:00', 470.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 25, 26);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-08 10:00:00', 4450.00, 'DEPOSIT', 'Monthly salary', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-09 10:00:00', 866.00, 'WITHDRAWAL', 'Rent', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-10 10:00:00', 63.00, 'WITHDRAWAL', 'Utilities', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-11 10:00:00', 48.00, 'WITHDRAWAL', 'Groceries', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-12 10:00:00', 210.00, 'DEPOSIT', 'Cash deposit', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-13 10:00:00', 73.00, 'WITHDRAWAL', 'Restaurant', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-14 10:00:00', 250.00, 'DEPOSIT', 'Freelance income', 'COMPLETED', 27, NULL);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-15 10:00:00', 480.00, 'TRANSFER', 'Transfer to savings', 'COMPLETED', 27, 28);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-16 10:00:00', 150.00, 'TRANSFER', 'Dinner reimbursement', 'COMPLETED', 1, 11);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-17 10:00:00', 85.00, 'TRANSFER', 'Shared expenses', 'COMPLETED', 4, 7);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-18 10:00:00', 200.00, 'TRANSFER', 'Family transfer', 'COMPLETED', 11, 2);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-19 10:00:00', 120.00, 'TRANSFER', 'Gift', 'COMPLETED', 18, 5);
INSERT INTO bank_transaction (transaction_date, amount, transaction_type, description, status, source_account_id, destination_account_id) VALUES ('2026-05-20 10:00:00', 95.00, 'TRANSFER', 'Shared purchase', 'COMPLETED', 23, 8);

-- =========================
-- Reconcile account balances
-- =========================
UPDATE account SET balance = 1431.00 WHERE account_id = 1;
UPDATE account SET balance = 1962.00 WHERE account_id = 2;
UPDATE account SET balance = 2047.00 WHERE account_id = 3;
UPDATE account SET balance = 1901.00 WHERE account_id = 4;
UPDATE account SET balance = 2133.00 WHERE account_id = 5;
UPDATE account SET balance = 2460.00 WHERE account_id = 6;
UPDATE account SET balance = 2342.00 WHERE account_id = 7;
UPDATE account SET balance = 2557.00 WHERE account_id = 8;
UPDATE account SET balance = 2723.00 WHERE account_id = 9;
UPDATE account SET balance = 2686.00 WHERE account_id = 10;
UPDATE account SET balance = 2229.00 WHERE account_id = 11;
UPDATE account SET balance = 410.00 WHERE account_id = 12;
UPDATE account SET balance = 2740.00 WHERE account_id = 13;
UPDATE account SET balance = 420.00 WHERE account_id = 14;
UPDATE account SET balance = 0.00 WHERE account_id = 15;
UPDATE account SET balance = 2475.00 WHERE account_id = 16;
UPDATE account SET balance = 430.00 WHERE account_id = 17;
UPDATE account SET balance = 2602.00 WHERE account_id = 18;
UPDATE account SET balance = 440.00 WHERE account_id = 19;
UPDATE account SET balance = 2949.00 WHERE account_id = 20;
UPDATE account SET balance = 450.00 WHERE account_id = 21;
UPDATE account SET balance = 0.00 WHERE account_id = 22;
UPDATE account SET balance = 2831.00 WHERE account_id = 23;
UPDATE account SET balance = 460.00 WHERE account_id = 24;
UPDATE account SET balance = 2895.00 WHERE account_id = 25;
UPDATE account SET balance = 470.00 WHERE account_id = 26;
UPDATE account SET balance = 3380.00 WHERE account_id = 27;
UPDATE account SET balance = 480.00 WHERE account_id = 28;
UPDATE account SET balance = 0.00 WHERE account_id = 29;
UPDATE account SET balance = 0.00 WHERE account_id = 30;
UPDATE account SET balance = 0.00 WHERE account_id = 31;
UPDATE account SET balance = 0.00 WHERE account_id = 32;
UPDATE account SET balance = 0.00 WHERE account_id = 33;

-- Customers 19 and 20 intentionally have no transactions.
-- Their accounts remain at a balance of 0.00.

-- End of demo data