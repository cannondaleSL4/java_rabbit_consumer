CREATE TABLE User_Table (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(50),
    age INTEGER,
    role INTEGER
);

CREATE TABLE User_Address (
    id SERIAL PRIMARY KEY,
    zip_code INTEGER,
    country VARCHAR(50),
    state VARCHAR(50),
    city VARCHAR(50),
    street VARCHAR(50),
    number_house INTEGER,
    number_apartment INTEGER
);

CREATE TABLE User_Address_User (
    address_id INTEGER REFERENCES User_Address(id),
    user_id INTEGER REFERENCES User_Table(id)
);

CREATE TABLE Product (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    price FLOAT,
    amount INTEGER,
    cost FLOAT
);

CREATE TABLE Users_Order (
    id UUID PRIMARY KEY,
    timestamp BIGINT,
    user_id INTEGER REFERENCES User_Table(id),
    address_id INTEGER REFERENCES User_Address(id),
    payed BOOLEAN,
    account_total FLOAT
);

CREATE TABLE Users_Order_Product (
    order_id UUID REFERENCES Users_Order(id),
    product_id INTEGER REFERENCES Product(id)
);