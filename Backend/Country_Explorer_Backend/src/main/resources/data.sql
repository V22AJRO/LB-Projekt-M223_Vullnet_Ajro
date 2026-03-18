-- =========================
-- REGIONS
-- =========================
INSERT IGNORE INTO regions (name) VALUES ('Europe');
INSERT IGNORE INTO regions (name) VALUES ('Asia');
INSERT IGNORE INTO regions (name) VALUES ('Americas');

-- =========================
-- LANGUAGES
-- =========================
INSERT IGNORE INTO languages (code, name) VALUES ('de', 'German');
INSERT IGNORE INTO languages (code, name) VALUES ('fr', 'French');
INSERT IGNORE INTO languages (code, name) VALUES ('it', 'Italian');
INSERT IGNORE INTO languages (code, name) VALUES ('en', 'English');
INSERT IGNORE INTO languages (code, name) VALUES ('ja', 'Japanese');

-- =========================
-- COUNTRIES
-- =========================
INSERT IGNORE INTO countries (code, name, region_id)
VALUES ('CHE', 'Switzerland',
        (SELECT id FROM regions WHERE name = 'Europe'));

INSERT IGNORE INTO countries (code, name, region_id)
VALUES ('DEU', 'Germany',
        (SELECT id FROM regions WHERE name = 'Europe'));

INSERT IGNORE INTO countries (code, name, region_id)
VALUES ('FRA', 'France',
        (SELECT id FROM regions WHERE name = 'Europe'));

INSERT IGNORE INTO countries (code, name, region_id)
VALUES ('USA', 'United States',
        (SELECT id FROM regions WHERE name = 'Americas'));

INSERT IGNORE INTO countries (code, name, region_id)
VALUES ('JPN', 'Japan',
        (SELECT id FROM regions WHERE name = 'Asia'));

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

INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'CHE'),
    (SELECT id FROM languages WHERE code = 'en')
);

-- Germany
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'DEU'),
    (SELECT id FROM languages WHERE code = 'de')
);

-- France
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'FRA'),
    (SELECT id FROM languages WHERE code = 'fr')
);

-- USA
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'USA'),
    (SELECT id FROM languages WHERE code = 'en')
);

-- Japan
INSERT IGNORE INTO country_languages (country_id, language_id)
VALUES (
    (SELECT id FROM countries WHERE code = 'JPN'),
    (SELECT id FROM languages WHERE code = 'ja')
);
