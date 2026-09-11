CREATE TABLE IF NOT EXISTS medical_records (
        id              UUID            PRIMARY KEY,
        appointment_id  UUID            NOT NULL,
        patient_id      UUID            NOT NULL,
        doctor_id       UUID            NOT NULL,
        nurse_id        UUID,
        scheduled_at    TIMESTAMP       NOT NULL,
        status          VARCHAR(30)     NOT NULL,
        reason          VARCHAR(500)    NOT NULL,
        notes           VARCHAR(2000),
        created_at      TIMESTAMP       NOT NULL,
        updated_at      TIMESTAMP       NOT NULL,

    CONSTRAINT chk_medical_record_status
    CHECK (status IN (
           'SCHEDULED',
           'CONFIRMED',
           'COMPLETED',
           'CANCELLED'
                     ))
    );
