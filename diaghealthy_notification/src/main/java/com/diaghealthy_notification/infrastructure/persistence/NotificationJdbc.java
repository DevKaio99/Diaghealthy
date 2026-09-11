package com.diaghealthy_notification.infrastructure.persistence;

import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.domain.enuns.NotificationStatus;
import com.diaghealthy_notification.domain.repositories.NotificationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NotificationJdbc implements NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Notification saveNotification(Notification notification) {

        String sql = """
                INSERT INTO notifications (
                    id,
                    appointment_id,
                    patient_id,
                    message,
                    status,
                    created_at,
                    sent_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                notification.getId(),
                notification.getAppointmentId(),
                notification.getPatientId(),
                notification.getMessage(),
                notification.getStatus().name(),
                notification.getCreatedAt(),
                notification.getSentAt()
        );

        return notification;
    }

    @Override
    public Notification updateNotification(Notification notification) {

        String sql = """
                UPDATE notifications
                SET
                    status = ?,
                    sent_at = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                notification.getStatus().name(),
                notification.getSentAt(),
                notification.getId()
        );

        return notification;
    }

    @Override
    public Optional<Notification> findNotificationById(UUID id) {

        String sql = """
                SELECT
                    id,
                    appointment_id,
                    patient_id,
                    message,
                    status,
                    created_at,
                    sent_at
                FROM notifications
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                id
        ).stream().findFirst();
    }

    @Override
    public List<Notification> findNotificationsByPatientId(UUID patientId) {

        String sql = """
                SELECT
                    id,
                    appointment_id,
                    patient_id,
                    message,
                    status,
                    created_at,
                    sent_at
                FROM notifications
                WHERE patient_id = ?
                ORDER BY created_at DESC
                """;

        return jdbcTemplate.query(
                sql,
                this::mapRow,
                patientId
        );
    }

    private Notification mapRow(java.sql.ResultSet rs, int rowNum)
            throws java.sql.SQLException {

        var sentAtTimestamp = rs.getTimestamp("sent_at");

        return Notification.reconstitute(
                rs.getObject("id", UUID.class),
                rs.getObject("appointment_id", UUID.class),
                rs.getObject("patient_id", UUID.class),
                rs.getString("message"),
                NotificationStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                sentAtTimestamp != null ? sentAtTimestamp.toLocalDateTime() : null
        );
    }
}
