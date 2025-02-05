-- Insert roles
INSERT INTO roles (name) VALUES ('CUSTOMER'), ('ADMIN');

-- Insert users
INSERT INTO users (username, email, password)
VALUES
    ('john_doe', 'john.doe@example.com', 'password123'),
    ('jane_smith', 'jane.smith@example.com', 'password123');

-- Map users to roles
INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),
                                              (2, 1);

-- Insert products
INSERT INTO products (name, description, price, stock)
VALUES
    ('Laptop', 'High-end gaming laptop', 1500.00, 10),
    ('Smartphone', 'Latest smartphone with advanced features', 800.00, 25),
    ('Headphones', 'Noise-canceling wireless headphones', 200.00, 15);

-- Insert orders (associated with user ID 1)
INSERT INTO orders (user_id, status, total_amount)
VALUES
    (1, 'PENDING', 1700.00);

-- Insert cart items (associated with order ID 1)
INSERT INTO cart (user_id, product_id, quantity)
VALUES
    (1, 1, 1),
    (1, 2, 1);

-- Insert payments
INSERT INTO payments (order_id, amount, payment_method)
VALUES
    (1, 1700.00, 'Credit Card');