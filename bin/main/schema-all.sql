DROP TABLE products IF EXISTS;

create table products(
id BIGINT(20) AUTO_INCREMENT,
product_code VARCHAR(20) NOT NULL,
name VARCHAR(100) NOT NULL,
kana VARCHAR(100) NOT NULL,
created_at DATETIME NOT NULL,
created_user_id INTEGER(8) NOT NULL,
updated_at DATETIME NOT NULL,
updated_user_id INTEGER(8) NOT NULL,
PRIMARY KEY(id)
);
