package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DoctorRepositoryJdbc implements DoctorRepository {
    private final JdbcTemplate jdbcTemplate;

    public DoctorRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        return Optional.empty();
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        return null;
    }

    @Override
    public Optional<Doctor> findByEmailIgnoreCase(String email) {
        return Optional.empty();
    }
}
