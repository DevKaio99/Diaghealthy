package com.diaghealthy_scheduling.infrastructure.persistence;

import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AppointmentJdbc implements AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {

        String sql = """
                INSERT INTO appointments (
                    id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getScheduledAt(),
                appointment.getStatus().name(),
                appointment.getReason(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );

        return appointment;
    }

    @Override
    public Optional<Appointment> findAppointmentById(UUID id) {

        String sql = """
                SELECT
                    id,
                    patient_id,
                    doctor_id,
                    nurse_id,
                    scheduled_at,
                    status,
                    reason,
                    created_at,
                    updated_at
                FROM appointments
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                id
        ).stream().findFirst();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {

        String sql = """
                UPDATE appointments
                SET
                    patient_id = ?,
                    doctor_id = ?,
                    nurse_id = ?,
                    scheduled_at = ?,
                    status = ?,
                    reason = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getScheduledAt(),
                appointment.getStatus().name(),
                appointment.getReason(),
                appointment.getUpdatedAt(),
                appointment.getId()
        );

        return appointment;
    }

    private Appointment mapRow(java.sql.ResultSet rs, int rowNum)
            throws java.sql.SQLException {

        return Appointment.reconstitute(
                rs.getObject("id", UUID.class),
                rs.getObject("patient_id", UUID.class),
                rs.getObject("doctor_id", UUID.class),
                rs.getObject("nurse_id", UUID.class),
                rs.getTimestamp("scheduled_at").toLocalDateTime(),
                AppointmentStatus.valueOf(rs.getString("status")),
                rs.getString("reason"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}