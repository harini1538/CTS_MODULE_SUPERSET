
DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'transactions') THEN
    EXECUTE 'DROP TABLE transactions CASCADE';
  END IF;

  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'loans') THEN
    EXECUTE 'DROP TABLE loans CASCADE';
  END IF;

  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'accounts') THEN
    EXECUTE 'DROP TABLE accounts CASCADE';
  END IF;

  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'customers') THEN
    EXECUTE 'DROP TABLE customers CASCADE';
  END IF;

  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'employees') THEN
    EXECUTE 'DROP TABLE employees CASCADE';
  END IF;
END
$$;


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
  (1, 'Harini', DATE '1985-05-15', 1000, CURRENT_DATE, FALSE),
  (2, 'Sanjana', DATE '1990-07-20', 15000, CURRENT_DATE, FALSE),
  (3, 'Tara', DATE '1950-01-01', 500, CURRENT_DATE, TRUE);

INSERT INTO Accounts VALUES 
  (1, 1, 'Savings', 1000, CURRENT_DATE),
  (2, 2, 'Checking', 15000, CURRENT_DATE),
  (3, 3, 'Savings', 500, CURRENT_DATE);

INSERT INTO Transactions VALUES 
  (1, 1, CURRENT_DATE, 200, 'Deposit'),
  (2, 2, CURRENT_DATE, 300, 'Withdrawal');

INSERT INTO Loans VALUES 
  (1, 1, 5000, 5, CURRENT_DATE, CURRENT_DATE + INTERVAL '60 months'),
  (2, 3, 10000, 6, CURRENT_DATE, CURRENT_DATE + INTERVAL '20 days');

INSERT INTO Employees VALUES 
  (1, 'Pooja', 'Manager', 70000, 'HR', DATE '2015-06-15'),
  (2, 'Dev', 'Developer', 60000, 'IT', DATE '2017-03-20');


CREATE OR REPLACE FUNCTION ProcessMonthlyInterest()
RETURNS VOID AS $$
BEGIN
  UPDATE Accounts
  SET Balance = Balance + (Balance * 0.01),
      LastModified = CURRENT_DATE
  WHERE AccountType = 'Savings';

  RAISE NOTICE 'Monthly interest applied to all savings accounts.';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION UpdateEmployeeBonus(p_department TEXT, p_bonus_percent NUMERIC)
RETURNS VOID AS $$
BEGIN
  UPDATE Employees
  SET Salary = Salary + (Salary * p_bonus_percent / 100)
  WHERE Department = p_department;

  RAISE NOTICE 'Salaries updated for department: %', p_department;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION TransferFunds(p_from_account INT, p_to_account INT, p_amount NUMERIC)
RETURNS VOID AS $$
DECLARE
  v_from_balance NUMERIC;
BEGIN
  SELECT Balance INTO v_from_balance FROM Accounts WHERE AccountID = p_from_account;

  IF v_from_balance < p_amount THEN
    RAISE EXCEPTION 'Insufficient funds in source account.';
  END IF;

  UPDATE Accounts
  SET Balance = Balance - p_amount,
      LastModified = CURRENT_DATE
  WHERE AccountID = p_from_account;

  UPDATE Accounts
  SET Balance = Balance + p_amount,
      LastModified = CURRENT_DATE
  WHERE AccountID = p_to_account;

  RAISE NOTICE 'Transferred % from account % to account %', p_amount, p_from_account, p_to_account;
EXCEPTION
  WHEN OTHERS THEN
    RAISE NOTICE 'Error: %', SQLERRM;
    RAISE;
END;
$$ LANGUAGE plpgsql;


DO $$
BEGIN
  PERFORM ProcessMonthlyInterest();
  PERFORM UpdateEmployeeBonus('IT', 5);
  PERFORM TransferFunds(1, 2, 200);
END;
$$;


DO $$
DECLARE
  rec RECORD;
BEGIN
  RAISE NOTICE E'\n--- Accounts Balances After Updates ---';
  RAISE NOTICE '%', 'AccountID | CustomerID | Type     | Balance     | LastModified';

  FOR rec IN SELECT * FROM Accounts ORDER BY AccountID LOOP
    RAISE NOTICE '%', 
      LPAD(rec.AccountID::TEXT, 9) || ' | ' ||
      LPAD(rec.CustomerID::TEXT, 11) || ' | ' ||
      RPAD(rec.AccountType, 8) || ' | ' ||
      TO_CHAR(rec.Balance, '9999990.00') || ' | ' ||
      TO_CHAR(rec.LastModified, 'DD-Mon-YYYY');
  END LOOP;
END;
$$;


DO $$
DECLARE
  rec RECORD;
BEGIN
  RAISE NOTICE E'\n--- Employees Salaries After Bonus ---';
  RAISE NOTICE '%', 'EmployeeID | Name              | Department | Salary';

  FOR rec IN SELECT * FROM Employees ORDER BY EmployeeID LOOP
    RAISE NOTICE '%',
      LPAD(rec.EmployeeID::TEXT, 10) || ' | ' ||
      RPAD(rec.Name, 17) || ' | ' ||
      RPAD(rec.Department, 10) || ' | ' ||
      TO_CHAR(rec.Salary, '9999990.00');
  END LOOP;
END;
$$;
