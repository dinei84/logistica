package com.logistica.auth;

import com.logistica.exception.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterDTO dto) {
        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new BadRequestException("Username já está em uso: " + dto.username());
        }

        UserModel user = new UserModel(
                dto.username(),
                passwordEncoder.encode(dto.password()),
                Role.USER
        );

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        UserModel user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }
}
