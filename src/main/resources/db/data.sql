-- Insert roles (if not exists)
MERGE INTO roles (id, name)
    KEY (name)
    VALUES (1, 'CUSTOMER'), (2, 'ADMIN');

-- Insert users (only if not already present)
MERGE INTO users (id, username, email, password)
    KEY (email)
    VALUES
    (1, 'john_doe', 'john.doe@example.com', 'password123'),
    (2, 'jane_smith', 'jane.smith@example.com', 'password123');

-- Map users to roles (idempotent insert)
MERGE INTO user_roles (user_id, role_id)
    KEY (user_id, role_id)
    VALUES (1, 1), (2, 1);

-- Insert products
MERGE INTO products (id, name, description, price, stock)
    KEY (name)
    VALUES
    (1, 'Laptop', 'High-end gaming laptop', 1500.00, 10),
    (2, 'Smartphone', 'Latest smartphone with advanced features', 800.00, 25),
    (3, 'Headphones', 'Noise-canceling wireless headphones', 200.00, 15);

-- Insert orders
MERGE INTO orders (id, user_id, status, total_amount)
    KEY (id)
    VALUES (1, 1, 'PENDING', 1700.00);

-- Insert cart items
MERGE INTO cart (id, user_id, product_id, quantity)
    KEY (id)
    VALUES
    (1, 1, 1, 1),
    (2, 1, 2, 1);

-- Insert payments
MERGE INTO payments (id, order_id, amount, payment_method)
    KEY (id)
    VALUES (1, 1, 1700.00, 'PAYPAL');