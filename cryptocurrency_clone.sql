CREATE DATABASE IF NOT EXISTS cryptocurrency_clone;

USE cryptocurrency_clone;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Cryptocurrencies Table
CREATE TABLE IF NOT EXISTS cryptocurrencies (
    coin_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    market_cap DECIMAL(20,2),
    change_24h DECIMAL(5,2)
);

-- Watchlist Table
CREATE TABLE IF NOT EXISTS watchlist (
    watchlist_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    coin_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (coin_id) REFERENCES cryptocurrencies(coin_id)
);

-- Portfolio Table
CREATE TABLE IF NOT EXISTS portfolio (
    portfolio_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    coin_id INT,
    quantity DECIMAL(18,8) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (coin_id) REFERENCES cryptocurrencies(coin_id)
);

-- Sample Cryptocurrency Data
INSERT INTO cryptocurrencies
(name, symbol, price, market_cap, change_24h)
VALUES
('Bitcoin', 'BTC', 65000.00, 1280000000000.00, 2.50),
('Ethereum', 'ETH', 3200.00, 385000000000.00, 1.80),
('Tether', 'USDT', 1.00, 112000000000.00, 0.10),
('BNB', 'BNB', 580.00, 85000000000.00, -0.50),
('Solana', 'SOL', 145.00, 68000000000.00, 3.20),
('XRP', 'XRP', 0.60, 33000000000.00, -1.20),
('Cardano', 'ADA', 0.45, 16000000000.00, 0.90),
('Dogecoin', 'DOGE', 0.12, 17000000000.00, 3.50),
('Avalanche', 'AVAX', 28.00, 11000000000.00, -0.80),
('Polkadot', 'DOT', 6.50, 9500000000.00, 1.40);
