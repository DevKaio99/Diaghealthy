package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DoctorRepositoryJdbc implements DoctorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DoctorJdbcMapper doctorJdbcMapper;

    public DoctorRepositoryJdbc(
            JdbcTemplate jdbcTemplate,
            DoctorJdbcMapper doctorJdbcMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.doctorJdbcMapper = doctorJdbcMapper;
    }

    @Override
    @Transactional
    public Doctor save(Doctor doctor) {

        String usersSql = """
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
                usersSql,
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPassword(),
                doctor.getDateLastUpdate(),
                doctor.getCreatedAt(),
                doctor.isActive(),
                doctor.getRole().name()
        );

        String doctorsSql = """
                INSERT INTO doctors (
                    id,
                    crm,
                    speciality
                )
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                doctorsSql,
                doctor.getId(),
                doctor.getCRM(),
                doctor.getSpeciality()
        );

        return doctor;
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
    public List<Doctor> doctorList() {

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
                    d.crm,
                    d.speciality
                FROM users u
                INNER JOIN doctors d
                    ON d.id = u.id
                WHERE u.role = 'DOCTOR'
                """;

        return jdbcTemplate.query(
                sql,
                doctorJdbcMapper
        );
    }

    @Override
    public Optional<Doctor> findDoctorById(UUID id) {

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
                    d.crm,
                    d.speciality
                FROM users u
                INNER JOIN doctors d
                    ON d.id = u.id
                WHERE u.id = ?
                  AND u.role = 'DOCTOR'
                """;

        return jdbcTemplate.query(
                        sql,
                        doctorJdbcMapper,
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public Doctor updateDoctor(Doctor doctor) {

        LocalDateTime now = LocalDateTime.now();

        String sqlUser = """
                UPDATE users
                SET
                    name = ?,
                    email = ?,
                    date_last_update = ?,
                    is_active = ?
                WHERE id = ?
                  AND role = 'DOCTOR'
                """;

        jdbcTemplate.update(
                sqlUser,
                doctor.getName(),
                doctor.getEmail(),
                now,
                doctor.isActive(),
                doctor.getId()
        );

        String sqlDoctor = """
                UPDATE doctors
                SET
                    crm = ?,
                    speciality = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sqlDoctor,
                doctor.getCRM(),
                doctor.getSpeciality(),
                doctor.getId()
        );

        return findDoctorById(doctor.getId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Médico não encontrado após atualização"
                        )
                );
    }

    @Override
    public Optional<Doctor> findByEmailIgnoreCase(String email) {

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
                    d.crm,
                    d.speciality
                FROM users u
                INNER JOIN doctors d
                    ON d.id = u.id
                WHERE LOWER(u.email) = LOWER(?)
                  AND u.role = 'DOCTOR'
                """;

        return jdbcTemplate.query(
                        sql,
                        doctorJdbcMapper,
                        email
                )
                .stream()
                .findFirst();
    }

    @Override
    public boolean findDoctorByCRM(String crm) {

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM doctors
                    WHERE crm = ?
                )
                """;

        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        sql,
                        Boolean.class,
                        crm
                )
        );
    }
}