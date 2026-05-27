CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(50) NOT NULL,
                       company_name VARCHAR(255),
                       is_regular BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(50) NOT NULL,
                          price_per_m2 NUMERIC(10, 2),
                          price_per_unit NUMERIC(10, 2),
                          price_per_rate_unit NUMERIC(10, 2),
                          rate_type VARCHAR(30),
                          width_cm INTEGER,
                          length_cm INTEGER,
                          thickness_mm INTEGER,
                          color VARCHAR(100),
                          sale_unit VARCHAR(30) NOT NULL
);

CREATE TABLE quotations (
                            id BIGSERIAL PRIMARY KEY,
                            quotation_number VARCHAR(50) NOT NULL UNIQUE,
                            user_id BIGINT NOT NULL REFERENCES users(id),
                            status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
                            created_at DATE NOT NULL DEFAULT CURRENT_DATE,
                            validity_days INTEGER NOT NULL DEFAULT 30,
                            subtotal NUMERIC(10, 2) DEFAULT 0.00,
                            discount_amount NUMERIC(10, 2) DEFAULT 0.00,
                            total NUMERIC(10, 2) DEFAULT 0.00,
                            notes TEXT
);

CREATE TABLE quotation_lines (
                                 id BIGSERIAL PRIMARY KEY,
                                 quotation_id BIGINT NOT NULL REFERENCES quotations(id) ON DELETE CASCADE,
                                 product_id BIGINT NOT NULL REFERENCES products(id),
                                 quantity NUMERIC(10, 2) NOT NULL,
                                 unit_price NUMERIC(10, 2) NOT NULL,
                                 discount_percent NUMERIC(5, 2) DEFAULT 0.00,
                                 line_total NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
                                 description VARCHAR(500)
);

CREATE INDEX idx_quotations_user_id ON quotations(user_id);
CREATE INDEX idx_quotations_status ON quotations(status);
CREATE INDEX idx_quotation_lines_quotation_id ON quotation_lines(quotation_id);