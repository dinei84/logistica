package com.logistica.auth;

import com.logistica.administrator.AdministratorRepository;
import com.logistica.controlelog.collaborator.CollaboratorRepository;
import com.logistica.controlelog.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CollaboratorRepository collaboratorRepository;
    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AuthService authService;

    private RegisterDTO adminDto;
    private RegisterDTO userDto;

    @BeforeEach
    void setUp() {
        adminDto = new RegisterDTO("admin", "123", "Admin Name", Role.ADMIN);
        userDto = new RegisterDTO("user", "123", "User Name", Role.USER);
    }

    @Test
    void register_ShouldSaveAdministrator_WhenRoleIsAdmin() {
        when(userRepository.findByUsername(adminDto.username())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_pw");
        when(userRepository.save(any(UserModel.class))).thenAnswer(i -> {
            UserModel saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });
        when(jwtService.generateToken(any(UserModel.class))).thenReturn("fake-token");

        AuthResponseDTO response = authService.register(adminDto);

        assertThat(response.token()).isEqualTo("fake-token");
        verify(userRepository, times(1)).save(any(UserModel.class));
        verify(administratorRepository, times(1)).save(any());
        verify(collaboratorRepository, never()).save(any());
    }

    @Test
    void register_ShouldSaveCollaborator_WhenRoleIsUser() {
        when(userRepository.findByUsername(userDto.username())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_pw");
        when(userRepository.save(any(UserModel.class))).thenAnswer(i -> {
            UserModel saved = i.getArgument(0);
            saved.setId(2L);
            return saved;
        });
        when(jwtService.generateToken(any(UserModel.class))).thenReturn("fake-token-user");

        AuthResponseDTO response = authService.register(userDto);

        assertThat(response.token()).isEqualTo("fake-token-user");
        verify(userRepository, times(1)).save(any(UserModel.class));
        verify(collaboratorRepository, times(1)).save(any());
        verify(administratorRepository, never()).save(any());
    }

    @Test
    void register_ShouldThrowBadRequest_WhenUsernameAlreadyExists() {
        when(userRepository.findByUsername(adminDto.username())).thenReturn(Optional.of(new UserModel()));

        assertThatThrownBy(() -> authService.register(adminDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Username já está em uso");

        verify(userRepository, never()).save(any());
        verify(administratorRepository, never()).save(any());
        verify(collaboratorRepository, never()).save(any());
    }
}
