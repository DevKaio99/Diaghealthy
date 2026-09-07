CREATE TABLE IF NOT EXISTS appointments (
        id              UUID            PRIMARY KEY,
        patient_id      UUID            NOT NULL,
        doctor_id       UUID            NOT NULL,
        nurse_id        UUID,
        scheduled_at    TIMESTAMP       NOT NULL,
        status          VARCHAR(30)     NOT NULL,
        reason          VARCHAR(500)    NOT NULL,
        created_at      TIMESTAMP       NOT NULL,
        updated_at      TIMESTAMP       NOT NULL,

    CONSTRAINT chk_appointment_status
    CHECK (status IN (
           'SCHEDULED',
           'CONFIRMED',
           'COMPLETED',
           'CANCELLED'
                     ))
    );