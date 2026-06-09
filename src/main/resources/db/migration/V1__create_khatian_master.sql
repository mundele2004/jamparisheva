CREATE TABLE IF NOT EXISTS khatian_master (
    id BIGSERIAL PRIMARY KEY,
    khatian_no VARCHAR(50),
    plot_no VARCHAR(50),
    owner_name VARCHAR(255),
    lgd_village_code VARCHAR(20),
    lgd_district_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_khatian_master_owner_village
    ON khatian_master (LOWER(owner_name), lgd_village_code);

CREATE INDEX IF NOT EXISTS idx_khatian_master_khatian_village
    ON khatian_master (khatian_no, lgd_village_code);

CREATE INDEX IF NOT EXISTS idx_khatian_master_plot_village
    ON khatian_master (plot_no, lgd_village_code);
