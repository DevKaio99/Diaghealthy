CREATE TABLE IF NOT EXISTS users (
    id                     UUID             PRIMARY KEY,
    name                   VARCHAR(255)     NOT NULL,
    email                  VARCHAR(255)     NOT NULL UNIQUE,
    password               VARCHAR(255)     NOT NULL,
    date_last_update       TIMESTAMP,
    created_at             TIMESTAMP,
    is_active              BOOLEAN          NOT NULL DEFAULT TRUE,
    role                   VARCHAR(20)      NOT NULL,

    CHECK (role IN ('DOCTOR', 'NURSE', 'PATIENT', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS patients (
    id                      UUID            PRIMARY KEY,
    date_of_birth           DATE            NOT NULL,

    CONSTRAINT fk_users_patients
       FOREIGN KEY (id)
       REFERENCES users(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS doctors (
    id                      UUID            PRIMARY KEY,
    crm                     VARCHAR(20)     NOT NULL UNIQUE,
    speciality              VARCHAR(100),

    CONSTRAINT fk_doctors_users
        FOREIGN KEY (id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS nurses (
    id UUID PRIMARY KEY,
    coren VARCHAR(20) NOT NULL UNIQUE,

    CONSTRAINT fk_nurses_users
    FOREIGN KEY (id)
    REFERENCES users(id)
    ON DELETE CASCADE
);

