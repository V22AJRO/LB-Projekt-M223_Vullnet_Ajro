-- =========================
-- REGIONS
-- =========================
INSERT IGNORE INTO regions (name) VALUES ('Europe');

-- =========================
-- COUNTRIES
-- =========================
INSERT IGNORE INTO countries (code, name, capital, population, president, region_id)
VALUES (
    'CHE',
    'Switzerland',
    'Bern',
    9051029,
    'Guy Parmelin',
    (SELECT id FROM regions WHERE name = 'Europe')
);

INSERT IGNORE INTO countries (code, name, capital, population, president, region_id)
VALUES (
    'DEU',
    'Germany',
    'Berlin',
    83577140,
    'Frank-Walter Steinmeier',
    (SELECT id FROM regions WHERE name = 'Europe')
);