package com.logistica.auth;

import com.logistica.exception.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.logistica.administrator.AdministratorModel;
import com.logistica.administrator.AdministratorRepository;
import com.logistica.collaborator.CollaboratorModel;
import com.logistica.collaborator.CollaboratorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CollaboratorRepository collaboratorRepository;
    private final AdministratorRepository administratorRepository;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       CollaboratorRepository collaboratorRepository,
                       AdministratorRepository administratorRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.collaboratorRepository = collaboratorRepository;
        this.administratorRepository = administratorRepository;
    }

    @Transactional
    public AuthResponseDTO register(RegisterDTO dto) {
        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new BadRequestException("Username já está em uso: " + dto.username());
        }

        Role role = dto.role() != null ? dto.role() : Role.USER;

        UserModel user = new UserModel(
                dto.username(),
                passwordEncoder.encode(dto.password()),
                role
        );

        user = userRepository.save(user);

        if (role == Role.ADMIN) {
            AdministratorModel admin = new AdministratorModel(null, dto.nome(), user);
            administratorRepository.save(admin);
        } else {
            CollaboratorModel collab = new CollaboratorModel(null, dto.nome(), user);
            collaboratorRepository.save(collab);
        }

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
