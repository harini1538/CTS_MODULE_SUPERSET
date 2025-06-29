
SET client_min_messages TO WARNING;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS Loans;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Customers;

CREATE TABLE Customers (
    CustomerID INTEGER PRIMARY KEY,
    Name VARCHAR(100),
    DOB DATE,
    Balance NUMERIC,
    LastModified DATE,
    IsVIP BOOLEAN DEFAULT FALSE
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
    TransactionType VARCHAR(10)
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


INSERT INTO Customers VALUES 
    (1, 'ANITA', '1960-04-10', 8000, CURRENT_DATE, FALSE),
    (2, 'RAHUL', '1985-09-25', 20000, CURRENT_DATE, FALSE),
    (3, 'KIRAN', '1945-02-28', 300, CURRENT_DATE, FALSE);

INSERT INTO Loans VALUES 
    (1, 1, 7000, 5, CURRENT_DATE, CURRENT_DATE + INTERVAL '60 days'),
    (2, 3, 2500, 4, CURRENT_DATE, CURRENT_DATE + INTERVAL '20 days');

UPDATE Customers
SET IsVIP = (Balance > 10000);


SELECT 
    l.LoanID, 
    c.Name AS CustomerName, 
    TO_CHAR(l.EndDate, 'DD-Mon-YYYY') AS DueDate
FROM Loans l
JOIN Customers c ON c.CustomerID = l.CustomerID
WHERE l.EndDate BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days';

SELECT 
    CustomerID, 
    Name, 
    Balance, 
    CASE WHEN IsVIP THEN 'Yes' ELSE 'No' END AS VIP_Status
FROM Customers;
