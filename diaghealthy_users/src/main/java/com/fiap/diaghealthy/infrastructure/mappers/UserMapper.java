package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.application.inputs.UserUpdatePassInput;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.infrastructure.dtos.users.UserResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.UserUpdatePassDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

        public UserUpdatePassInput toUpdatePassInput (UserUpdatePassDTO userUpdatePassDTO) {
            return new UserUpdatePassInput(
                    userUpdatePassDTO.oldPassword(),
                    userUpdatePassDTO.newPassword()
            );
        }

        public UserResponseDTO toDto (User user) {
            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getDateLastUpdate()
            );
        }

}
