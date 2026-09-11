CREATE TABLE IF NOT EXISTS notifications (
        id              UUID            PRIMARY KEY,
        appointment_id  UUID            NOT NULL,
        patient_id      UUID            NOT NULL,
        message         VARCHAR(500)    NOT NULL,
        status          VARCHAR(30)     NOT NULL,
        created_at      TIMESTAMP       NOT NULL,
        sent_at         TIMESTAMP,

    CONSTRAINT chk_notification_status
    CHECK (status IN (
           'PENDING',
           'SENT',
           'FAILED'
                     ))
    );
