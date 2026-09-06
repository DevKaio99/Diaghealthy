package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.domain.entities.Nurse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class NurseJdbcMapper implements RowMapper<Nurse> {
    @Override
    public Nurse mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Nurse.reconstitute(
                rs.getObject("user_id", UUID.class),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("date_last_update").toLocalDateTime(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getBoolean("is_active"),
                rs.getString("coren")
        );

    }
}
