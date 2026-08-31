package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.infrastructure.dtos.users.UserAutenticationDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.UserLoginResponseDTO;
import com.fiap.diaghealthy.infrastructure.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Autenticação de usuários")
public class UserAuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserAuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Autenticação de Usuário", description = "Realiza a validação de usuário do banco de dados através de login (e-mail) e senha para a liberação do uso das requisições dos Controllers via Token gerado.")
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid UserAutenticationDTO dto) {

            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((UserDetails) auth.getPrincipal());

            return ResponseEntity.ok(new UserLoginResponseDTO(token));
    }
}