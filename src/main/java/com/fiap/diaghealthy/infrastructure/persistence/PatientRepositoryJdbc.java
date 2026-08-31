package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.Patient;
import com.fiap.diaghealthy.domain.repositories.PatientRepository;
import com.fiap.diaghealthy.infrastructure.mappers.PatientJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PatientRepositoryJdbc implements PatientRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PatientJdbcMapper patientJdbcMapper;

    public PatientRepositoryJdbc(
            JdbcTemplate jdbcTemplate,
            PatientJdbcMapper patientJdbcMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.patientJdbcMapper = patientJdbcMapper;
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {

        String userSql = """
                INSERT INTO users (
                    id,
                    name,
                    email,
                    password,
                    date_last_update,
                    created_at,
                    is_active,
                    role
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                userSql,
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getDateLastUpdate(),
                patient.getCreatedAt(),
                patient.isActive(),
                patient.getRole().name()
        );

        String patientSql = """
                INSERT INTO patients (
                    id,
                    date_of_birth
                )
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                patientSql,
                patient.getId(),
                patient.getDateOfBirth()
        );

        return patient;
    }

    @Override
    public boolean validateEmailExists(String email) {

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM users
                    WHERE LOWER(email) = LOWER(?)
                )
                """;

        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        sql,
                        Boolean.class,
                        email
                )
        );
    }

    @Override
    public List<Patient> patientList() {

        String sql = """
                SELECT
                    u.id AS user_id,
                    u.name,
                    u.email,
                    u.password,
                    u.date_last_update,
                    u.created_at,
                    u.is_active,
                    u.role,
                    p.date_of_birth
                FROM users u
                INNER JOIN patients p
                    ON p.id = u.id
                WHERE u.role = 'PATIENT'
                """;

        return jdbcTemplate.query(
                sql,
                patientJdbcMapper
        );
    }

    @Override
    public Optional<Patient> findPatientById(UUID id) {

        String sql = """
                SELECT
                    u.id AS user_id,
                    u.name,
                    u.email,
                    u.password,
                    u.date_last_update,
                    u.created_at,
                    u.is_active,
                    u.role,
                    p.date_of_birth
                FROM users u
                INNER JOIN patients p
                    ON p.id = u.id
                WHERE u.id = ?
                  AND u.role = 'PATIENT'
                """;

        return jdbcTemplate.query(
                        sql,
                        patientJdbcMapper,
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public Patient updatePatient(Patient patient) {

        LocalDateTime now = LocalDateTime.now();

        String userSql = """
                UPDATE users
                SET
                    name = ?,
                    email = ?,
                    password = ?,
                    date_last_update = ?,
                    is_active = ?
                WHERE id = ?
                  AND role = 'PATIENT'
                """;

        jdbcTemplate.update(
                userSql,
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                now,
                patient.isActive(),
                patient.getId()
        );

        String patientSql = """
                UPDATE patients
                SET
                    date_of_birth = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                patientSql,
                patient.getDateOfBirth(),
                patient.getId()
        );

        return findPatientById(patient.getId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Paciente não encontrado após atualização"
                        )
                );
    }

    @Override
    public Optional<Patient> findByEmailIgnoreCase(String email) {

        String sql = """
                SELECT
                    u.id AS user_id,
                    u.name,
                    u.email,
                    u.password,
                    u.date_last_update,
                    u.created_at,
                    u.is_active,
                    u.role,
                    p.date_of_birth
                FROM users u
                INNER JOIN patients p
                    ON p.id = u.id
                WHERE LOWER(u.email) = LOWER(?)
                  AND u.role = 'PATIENT'
                """;

        return jdbcTemplate.query(
                        sql,
                        patientJdbcMapper,
                        email
                )
                .stream()
                .findFirst();
    }
}
