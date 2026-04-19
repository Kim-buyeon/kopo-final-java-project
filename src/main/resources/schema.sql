-- 1. 모든 테이블 삭제 (순서 엄수)
-- DROP TABLE cartitem CASCADE CONSTRAINTS;
-- DROP TABLE orderitem CASCADE CONSTRAINTS;
-- DROP TABLE products CASCADE CONSTRAINTS;
-- DROP TABLE category CASCADE CONSTRAINTS;
-- DROP TABLE member CASCADE CONSTRAINTS;
-- COMMIT;

CREATE TABLE member (
    id VARCHAR2(10) PRIMARY KEY,
    password VARCHAR2(15) NOT NULL,
    name VARCHAR2(15) NOT NULL, 
    telno VARCHAR2(15) NOT NULL,
    status VARCHAR2(15),
    member_classification VARCHAR2(10) NOT NULL CHECK (member_classification IN ('MEMBER', 'MANAGER'))
);

CREATE TABLE category (
    category_id VARCHAR2(10) PRIMARY KEY,
    name VARCHAR2(20) NOT NULL,
    sortorder VARCHAR2(10),
    parent_category_id VARCHAR2(10) REFERENCES category(category_id)
);

CREATE TABLE products (
    product_id VARCHAR2(10) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    description CLOB,
    price NUMBER DEFAULT 0,
    stock NUMBER DEFAULT 0,
    product_status VARCHAR2(15) NOT NULL CHECK (product_status IN ('NORMAL', 'SUSPENSION'))
);

CREATE TABLE orderitem (
    order_id VARCHAR2(10) PRIMARY KEY,
    member_id VARCHAR2(10) NOT NULL REFERENCES member(id),
    product_id VARCHAR2(10) NOT NULL REFERENCES product(product_id),
    quantity NUMBER NOT NULL
);

CREATE TABLE cartitem (
    cart_id VARCHAR2(10) PRIMARY KEY,
    member_id VARCHAR2(10) NOT NULL REFERENCES member(id),
    product_id VARCHAR2(10) NOT NULL REFERENCES product(product_id),
    quantity NUMBER NOT NULL
);
COMMIT;