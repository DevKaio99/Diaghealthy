package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.UpdatePasswordUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.UserResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.UserUpdatePassDTO;
import com.fiap.diaghealthy.infrastructure.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UserMapper userMapper;

    public UserController(UpdatePasswordUseCase updatePasswordUseCase, UserMapper userMapper) {
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.userMapper = userMapper;
    }


    @Operation(summary = "Alteração de Senha", description = "Altera a senha do usuário especificado pelo ID exigindo a senha atual deste usuário e a senha nova")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha atual incorreta",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("changepass/{id}")
    public ResponseEntity<UserResponseDTO> updatePass(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UserUpdatePassDTO dto
    ) throws UnauthorizedException {

        var updatePasswordInput = userMapper.toUpdatePassInput(dto);
        var updatedPassword = updatePasswordUseCase.execute(id, updatePasswordInput);
        UserResponseDTO userResponseDTO = userMapper.toDto(updatedPassword);

        return ResponseEntity.ok(userResponseDTO);

    }
}
