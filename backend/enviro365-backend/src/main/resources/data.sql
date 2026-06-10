INSERT INTO investor (id, full_name, age, email)
VALUES
(1, 'John Smith', 70, 'john.smith@email.com'),
(2, 'Mary Johnson', 60, 'mary.johnson@email.com');

INSERT INTO product (id, product_name, product_type, current_balance, investor_id)
VALUES
(1, 'Retirement Annuity', 'RETIREMENT', 500000.00, 1),
(2, 'Tax Free Savings', 'SAVINGS', 120000.00, 1),
(3, 'Retirement Fund', 'RETIREMENT', 300000.00, 2),
(4, 'Unit Trust', 'INVESTMENT', 90000.00, 2);