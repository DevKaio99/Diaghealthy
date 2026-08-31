package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.Nurse;
import com.fiap.diaghealthy.domain.repositories.NurseRepository;
import com.fiap.diaghealthy.infrastructure.mappers.NurseJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NurseRepositoryJdbc implements NurseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NurseJdbcMapper nurseJdbcMapper;

    public NurseRepositoryJdbc(
            JdbcTemplate jdbcTemplate,
            NurseJdbcMapper nurseJdbcMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.nurseJdbcMapper = nurseJdbcMapper;
    }

    @Override
    @Transactional
    public Nurse save(Nurse nurse) {

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
                nurse.getId(),
                nurse.getName(),
                nurse.getEmail(),
                nurse.getPassword(),
                nurse.getDateLastUpdate(),
                nurse.getCreatedAt(),
                nurse.isActive(),
                nurse.getRole().name()
        );

        String nurseSql = """
                INSERT INTO nurses (
                    id,
                    coren
                )
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                nurseSql,
                nurse.getId(),
                nurse.getCoren()
        );

        return nurse;
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
    public List<Nurse> nurseList() {

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
                    n.coren
                FROM users u
                INNER JOIN nurses n
                    ON n.id = u.id
                WHERE u.role = 'NURSE'
                """;

        return jdbcTemplate.query(
                sql,
                nurseJdbcMapper
        );
    }

    @Override
    public Optional<Nurse> findNurseById(UUID id) {

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
                    n.coren
                FROM users u
                INNER JOIN nurses n
                    ON n.id = u.id
                WHERE u.id = ?
                  AND u.role = 'NURSE'
                """;

        return jdbcTemplate.query(
                        sql,
                        nurseJdbcMapper,
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public Nurse updateNurse(Nurse nurse) {

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
                  AND role = 'NURSE'
                """;

        jdbcTemplate.update(
                userSql,
                nurse.getName(),
                nurse.getEmail(),
                nurse.getPassword(),
                now,
                nurse.isActive(),
                nurse.getId()
        );

        String nurseSql = """
                UPDATE nurses
                SET
                    coren = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                nurseSql,
                nurse.getCoren(),
                nurse.getId()
        );

        return findNurseById(nurse.getId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Enfermeiro não encontrado após atualização"
                        )
                );
    }

    @Override
    public Optional<Nurse> findByEmailIgnoreCase(String email) {

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
                    n.coren
                FROM users u
                INNER JOIN nurses n
                    ON n.id = u.id
                WHERE LOWER(u.email) = LOWER(?)
                  AND u.role = 'NURSE'
                """;

        return jdbcTemplate.query(
                        sql,
                        nurseJdbcMapper,
                        email
                )
                .stream()
                .findFirst();
    }

    @Override
    public boolean findNurseByCoren(String coren) {

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM nurses
                    WHERE coren = ?
                )
                """;

        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        sql,
                        Boolean.class,
                        coren
                )
        );
    }
}

