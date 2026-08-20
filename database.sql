-- 1. Énumération
CREATE TYPE expense_frequency AS ENUM ('NONE', 'MONTHLY', 'WEEKLY', 'YEARLY');

-- 2. Table User
CREATE TABLE "user" (
                        id VARCHAR(5) PRIMARY KEY,
                        ref VARCHAR(10) NOT NULL,
                        first_name VARCHAR(25) NOT NULL,
                        last_name VARCHAR(25) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        phone VARCHAR(20) NOT NULL
);

-- 3. Table Mère CashFlow
CREATE TABLE cashflow (
                          id VARCHAR(5) PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          amount NUMERIC NOT NULL,
                          id_user VARCHAR(5) REFERENCES "user"(id)
);

-- 4. Table Fille Donation (hérite de cashflow)
CREATE TABLE donation (
                          comment TEXT NOT NULL
) INHERITS (cashflow);

-- 5. Table Fille Expense (hérite de cashflow)
CREATE TABLE expense (
                         reason TEXT NOT NULL,
                         frequency expense_frequency NOT NULL
) INHERITS (cashflow);

