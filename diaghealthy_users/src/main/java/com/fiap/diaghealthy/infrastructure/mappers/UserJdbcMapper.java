package com.fiap.diaghealthy.infrastructure.mappers;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.enuns.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserJdbcMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        UUID id = rs.getObject("user_id", UUID.class);
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");

        LocalDateTime dateLastUpdate =
                rs.getTimestamp("date_last_update").toLocalDateTime();

        LocalDateTime createdAt =
                rs.getTimestamp("created_at").toLocalDateTime();

        boolean isActive = rs.getBoolean("is_active");

        Role role = Role.valueOf(rs.getString("role"));

        return User.reconstitute(
                id,
                name,
                email,
                password,
                dateLastUpdate,
                createdAt,
                isActive,
                role
        );
    }
}