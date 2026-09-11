package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.application.inputs.UserUpdatePassInput;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.infrastructure.dtos.users.user.UserResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.user.UserUpdatePassDTO;
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
                    user.isActive(),
                    user.getDateLastUpdate(),
                    user.getRole()
            );
        }

}
