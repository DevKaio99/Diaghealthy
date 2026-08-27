package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorJdbcMapper;
import com.fiap.diaghealthy.infrastructure.mappers.UserJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DoctorRepositoryJdbc implements DoctorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DoctorJdbcMapper doctorJdbcMapper;

    public DoctorRepositoryJdbc(JdbcTemplate jdbcTemplate, UserJdbcMapper userJdbcMapper, DoctorJdbcMapper doctorJdbcMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.doctorJdbcMapper = doctorJdbcMapper;
    }

    @Override
    public Doctor save(Doctor doctor) {
        String usersSql = """
        INSERT INTO users (
            id,
            name,
            email,
            password,
            date_last_update,
            created_at,
            role
        )
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                usersSql,
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPassword(),
                doctor.getDateLastUpdate(),
                doctor.getCreatedAt(),
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
        return false;
    }

    @Override
    public List<Doctor> doctorList() {
        return List.of();
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
            INNER JOIN doctors d ON d.id = u.id
            WHERE u.id = ?
            """;

        return jdbcTemplate.query(
                sql,
                doctorJdbcMapper,
                id
        ).stream().findFirst();
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        String sqlUser = """
        UPDATE users
        SET
            name = ?,
            email = ?,
            date_last_update = ?
        WHERE id = ?
        """;

        jdbcTemplate.update(
                sqlUser,
                doctor.getName(),
                doctor.getEmail(),
                doctor.getDateLastUpdate(),
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

        return doctor;
    }

    @Override
    public Optional<Doctor> findByEmailIgnoreCase(String email) {
        return Optional.empty();
    }
}
