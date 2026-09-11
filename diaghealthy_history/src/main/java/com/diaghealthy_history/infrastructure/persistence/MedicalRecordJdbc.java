package com.diaghealthy_history.infrastructure.persistence;

import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.enuns.HistoryStatus;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MedicalRecordJdbc implements MedicalRecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicalRecordJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MedicalRecord saveRecord(MedicalRecord record) {

        String sql = """
                INSERT INTO medical_records (
                    id,
                    appointment_id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    notes,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                record.getId(),
                record.getAppointmentId(),
                record.getPatientId(),
                record.getDoctorId(),
                record.getNurseId(),
                record.getScheduledAt(),
                record.getStatus().name(),
                record.getReason(),
                record.getNotes(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );

        return record;
    }

    @Override
    public MedicalRecord updateRecord(MedicalRecord record) {

        String sql = """
                UPDATE medical_records
                SET
                    status = ?,
                    notes = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                record.getStatus().name(),
                record.getNotes(),
                record.getUpdatedAt(),
                record.getId()
        );

        return record;
    }

    @Override
    public Optional<MedicalRecord> findRecordById(UUID id) {

        String sql = """
                SELECT
                    id,
                    appointment_id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    notes,
                    created_at,
                    updated_at
                FROM medical_records
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                id
        ).stream().findFirst();
    }

    @Override
    public List<MedicalRecord> findRecordsByPatientId(UUID patientId) {

        String sql = """
                SELECT
                    id,
                    appointment_id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    notes,
                    created_at,
                    updated_at
                FROM medical_records
                WHERE patient_id = ?
                ORDER BY scheduled_at DESC
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                patientId
        );
    }

    @Override
    public List<MedicalRecord> findFutureRecordsByPatientId(UUID patientId, LocalDateTime from) {

        String sql = """
                SELECT
                    id,
                    appointment_id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    notes,
                    created_at,
                    updated_at
                FROM medical_records
                WHERE patient_id = ? AND scheduled_at > ?
                ORDER BY scheduled_at ASC
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                patientId,
                from
        );
    }

    private MedicalRecord mapRow(java.sql.ResultSet rs, int rowNum)
            throws java.sql.SQLException {

        return MedicalRecord.reconstitute(
                rs.getObject("id", UUID.class),
                rs.getObject("appointment_id", UUID.class),
                rs.getObject("patient_id", UUID.class),
                rs.getObject("doctor_id", UUID.class),
                rs.getObject("nurse_id", UUID.class),
                rs.getTimestamp("scheduled_at").toLocalDateTime(),
                HistoryStatus.valueOf(rs.getString("status")),
                rs.getString("reason"),
                rs.getString("notes"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
