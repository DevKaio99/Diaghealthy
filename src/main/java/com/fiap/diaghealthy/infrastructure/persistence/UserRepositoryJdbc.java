package com.fiap.diaghealthy.infrastructure.persistence;

import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.repositories.UserRepository;
import com.fiap.diaghealthy.infrastructure.mappers.UserJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserJdbcMapper userJdbcMapper;

    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate, UserJdbcMapper userJdbcMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userJdbcMapper = userJdbcMapper;
    }


    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        String sql = """
        SELECT
            u.id AS user_id,
            u.name,
            u.email,
            u.password,
            u.date_last_update,
            u.created_at,
            u.is_active,
            u.role
        FROM users u
        WHERE LOWER(u.email) = LOWER(?)
        """;

        return jdbcTemplate.query(
                sql,
                userJdbcMapper,
                email
        ).stream().findFirst();
    }

    @Override
    public User saveUser(User user) {
        String sql = """
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
                sql,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateLastUpdate(),
                user.getCreatedAt(),
                user.isActive(),
                user.getRole().name()
        );

        return user;
    }
}
