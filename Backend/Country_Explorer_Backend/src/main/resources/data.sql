-- =========================
-- REGIONS
-- =========================
INSERT IGNORE INTO regions (name) VALUES ('Europe');

-- =========================
-- LANGUAGES
-- =========================
INSERT IGNORE INTO languages (code, name) VALUES ('de', 'German');
INSERT IGNORE INTO languages (code, name) VALUES ('fr', 'French');
INSERT IGNORE INTO languages (code, name) VALUES ('it', 'Italian');

-- =========================
-- COUNTRIES
-- =========================
INSERT IGNORE INTO countries (code, name, capital, population, president, region_id)
VALUES ('CHE', 'Switzerland', 'Bern', 9051029, 'Guy Parmelin',
        (SELECT id FROM regions WHERE name = 'Europe'));

INSERT IGNORE INTO countries (code, name, capital, population, president, region_id)
VALUES ('DEU', 'Germany', 'Berlin', 83577140, 'Frank-Walter Steinmeier',
        (SELECT id FROM regions WHERE name = 'Europe'));

-- =========================
-- COUNTRY_LANGUAGES
-- =========================

-- Switzerland
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'CHE'),
    (SELECT id FROM languages WHERE code = 'de')
);

INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'CHE'),
    (SELECT id FROM languages WHERE code = 'fr')
);

INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'CHE'),
    (SELECT id FROM languages WHERE code = 'it')
);

-- Germany
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'DEU'),
    (SELECT id FROM languages WHERE code = 'de')
);