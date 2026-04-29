package com.logistica.auth;

import com.logistica.controlelog.collaborator.CollaboratorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private CollaboratorRepository collaboratorRepository;

    @Test
    void register_ShouldRollbackUserModel_WhenProfileSaveFails() {
        // Arrange
        RegisterDTO dto = new RegisterDTO("rollback_user", "123", "Rollback Tester", Role.USER);
        
        // Simular falha banco de dados ao salvar o colaborador
        doThrow(new RuntimeException("Simulated Database Error")).when(collaboratorRepository).save(any());

        // Act & Assert
        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Simulated Database Error");

        // Verify Rollback: Como a transação falhou, o usuário não deve existir no banco
        Optional<UserModel> userInDb = userRepository.findByUsername("rollback_user");
        assertThat(userInDb).isEmpty();
    }
}
