
DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'transactions') THEN
    EXECUTE 'DROP TABLE transactions CASCADE';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'accounts') THEN
    EXECUTE 'DROP TABLE accounts CASCADE';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'loans') THEN
    EXECUTE 'DROP TABLE loans CASCADE';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'employees') THEN
    EXECUTE 'DROP TABLE employees CASCADE';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'customers') THEN
    EXECUTE 'DROP TABLE customers CASCADE';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'transactions_seq') THEN
    EXECUTE 'DROP SEQUENCE transactions_seq';
  END IF;
  IF EXISTS (SELECT 1 FROM pg_class WHERE relname = 'log_table') THEN
    EXECUTE 'DROP TABLE log_table';
  END IF;
END $$;

CREATE TABLE Customers (
    CustomerID INTEGER PRIMARY KEY,
    Name VARCHAR(100),
    DOB DATE,
    Balance NUMERIC,
    LastModified DATE
);

CREATE TABLE Accounts (
    AccountID INTEGER PRIMARY KEY,
    CustomerID INTEGER REFERENCES Customers(CustomerID),
    AccountType VARCHAR(20),
    Balance NUMERIC,
    LastModified DATE
);

CREATE TABLE Transactions (
    TransactionID INTEGER PRIMARY KEY,
    AccountID INTEGER REFERENCES Accounts(AccountID),
    TransactionDate DATE,
    Amount NUMERIC,
    TransactionType VARCHAR(20)
);

CREATE TABLE Loans (
    LoanID INTEGER PRIMARY KEY,
    CustomerID INTEGER REFERENCES Customers(CustomerID),
    LoanAmount NUMERIC,
    InterestRate NUMERIC,
    StartDate DATE,
    EndDate DATE
);

CREATE TABLE Employees (
    EmployeeID INTEGER PRIMARY KEY,
    Name VARCHAR(100),
    Position VARCHAR(50),
    Salary NUMERIC,
    Department VARCHAR(50),
    HireDate DATE
);

CREATE SEQUENCE Transactions_seq START WITH 3;

CREATE TABLE Log_Table (
    LogTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Message TEXT
);


INSERT INTO Customers VALUES (1, 'May', '1985-05-15', 1000, CURRENT_DATE);
INSERT INTO Customers VALUES (2, 'Smith', '1990-07-20', 1500, CURRENT_DATE);

INSERT INTO Accounts VALUES (1, 1, 'Savings', 1000, CURRENT_DATE);
INSERT INTO Accounts VALUES (2, 2, 'Checking', 1500, CURRENT_DATE);

INSERT INTO Transactions VALUES (1, 1, CURRENT_DATE, 200, 'Deposit');
INSERT INTO Transactions VALUES (2, 2, CURRENT_DATE, 300, 'Withdrawal');

INSERT INTO Loans VALUES (1, 1, 5000, 5, CURRENT_DATE, CURRENT_DATE + INTERVAL '60 days');

INSERT INTO Employees VALUES (1, 'Peter', 'Manager', 70000, 'HR', '2015-06-15');
INSERT INTO Employees VALUES (2, 'Harry ', 'Developer', 60000, 'IT', '2017-03-20');


CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    IN p_from_account INTEGER,
    IN p_to_account INTEGER,
    IN p_amount NUMERIC
)
LANGUAGE plpgsql AS $$
DECLARE
    v_balance NUMERIC;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_from_account FOR UPDATE;

    IF v_balance < p_amount THEN
        INSERT INTO Log_Table(Message) VALUES ('Transfer failed: Insufficient funds');
        RETURN;
    END IF;

    UPDATE Accounts SET Balance = Balance - p_amount, LastModified = CURRENT_DATE WHERE AccountID = p_from_account;
    UPDATE Accounts SET Balance = Balance + p_amount, LastModified = CURRENT_DATE WHERE AccountID = p_to_account;

    INSERT INTO Transactions VALUES (nextval('Transactions_seq'), p_from_account, CURRENT_DATE, p_amount, 'Transfer-Out');
    INSERT INTO Transactions VALUES (nextval('Transactions_seq'), p_to_account, CURRENT_DATE, p_amount, 'Transfer-In');

    INSERT INTO Log_Table(Message) VALUES ('Transfer successful.');
EXCEPTION
    WHEN OTHERS THEN
        INSERT INTO Log_Table(Message) VALUES ('Transfer failed: ' || SQLERRM);
END
$$;

CREATE OR REPLACE PROCEDURE UpdateSalary(
    IN p_emp_id INTEGER,
    IN p_percent NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_percent / 100)
    WHERE EmployeeID = p_emp_id;

    IF NOT FOUND THEN
        INSERT INTO Log_Table(Message) VALUES ('Salary update failed: Employee not found');
        RETURN;
    END IF;

    INSERT INTO Log_Table(Message) VALUES ('Salary updated.');
EXCEPTION
    WHEN OTHERS THEN
        INSERT INTO Log_Table(Message) VALUES ('Salary update failed: ' || SQLERRM);
END
$$;

CREATE OR REPLACE PROCEDURE AddNewCustomer(
    IN p_customer_id INTEGER,
    IN p_name VARCHAR,
    IN p_dob DATE,
    IN p_balance NUMERIC
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO Customers VALUES (p_customer_id, p_name, p_dob, p_balance, CURRENT_DATE);
    INSERT INTO Log_Table(Message) VALUES ('Customer added.');
EXCEPTION
    WHEN unique_violation THEN
        INSERT INTO Log_Table(Message) VALUES ('Customer ID already exists.');
    WHEN OTHERS THEN
        INSERT INTO Log_Table(Message) VALUES ('Failed to add customer: ' || SQLERRM);
END
$$;


CALL SafeTransferFunds(1, 2, 500);
CALL SafeTransferFunds(1, 2, 99999);  
CALL UpdateSalary(2, 10);             
CALL UpdateSalary(99, 10);            
CALL AddNewCustomer(3, 'Harini', '2004-06-29', 1200);  
CALL AddNewCustomer(1, 'Tesh', '2000-01-01', 1000);


SELECT * FROM Log_Table;
SELECT * FROM Customers;
SELECT * FROM Accounts;
SELECT * FROM Transactions;
SELECT * FROM Employees;
