package com.logistica.collaborator;

// ===== IMPORTS =====
// Você vai precisar destes imports. Se o IntelliJ reclamar que algo não existe,
// clique em Alt+Enter para importar automaticamente.

import com.logistica.controlelog.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

// ===== ESTRUTURA DE TESTE =====
// @ExtendWith(MockitoExtension.class) → ativa o Mockito para esta classe de teste
@ExtendWith(MockitoExtension.class)
class CollaboratorServiceTest {

    // === MOCKS ===
    // @Mock → cria um "dublê" do CollaboratorRepository
    @Mock
    private CollaboratorRepository repository;

    // === INJEÇÃO ===
    // @InjectMocks → cria uma instância do CollaboratorService
    // e injeta automaticamente o repository mock dentro dele
    @InjectMocks
    private CollaboratorService service;

    // === DADOS DE TESTE ===
    // Vamos reutilizar esses dados em todos os testes
    private CollaboratorModel collaboratorModel;
    private CollaboratorDTO collaboratorDTO;

    // === SETUP ===
    // @BeforeEach → executa ANTES de cada @Test
    // Aqui preparamos os dados que vamos usar
    @BeforeEach
    void setUp() {
        // Criando um CollaboratorModel FAKE para usar nos testes
        collaboratorModel = new CollaboratorModel(1L, "João Silva", null);

        // Criando um CollaboratorDTO FAKE
        collaboratorDTO = new CollaboratorDTO(1L, "João Silva");
    }

    // ========================================
    // TESTE 1: Buscar colaborador com SUCESSO
    // ========================================
    @Test
    void getCollaboratorById_ShouldReturnDTO_WhenCollaboratorExists() {

        // === ARRANGE (Preparação) ===
        // Aqui você configura o mock para retornar algo quando chamarem repository.findById(1L)
        // INSTRUÇÃO: Complete a linha abaixo
        // when(repository.findById(...)).thenReturn(Optional.of(...));

        // PREENCHA AQUI:
        when(repository.findById(1L)).thenReturn(Optional.of(collaboratorModel));

        // === ACT (Execução) ===
        // Aqui você chama o método que quer testar
        // INSTRUÇÃO: Chame service.getCollaboratorById(1L) e guarde o resultado em uma variável
        // Exemplo: CollaboratorDTO result = service.???

        // PREENCHA AQUI:
        CollaboratorDTO resultado = service.getCollaboratorById(1L);

        // === ASSERT (Verificação) ===
        // Aqui você verifica se o resultado é igual ao esperado
        // INSTRUÇÃO: Verifique se result.name() é igual a "João Silva" E se result.id() é 1L
        // Exemplo: assertThat(result.???()).isEqualTo(...);

        // PREENCHA AQUI (2 assertions):
        assertThat(resultado.name()).isEqualTo("João Silva");
        assertThat(resultado.id()).isEqualTo(1L);
    }

    // ========================================
    // TESTE 2: Buscar colaborador que NÃO existe
    // ========================================
    @Test
    void getCollaboratorById_ShouldThrowException_WhenCollaboratorDoesNotExist() {

        // === ARRANGE ===
        // Aqui você configura o mock para retornar Optional.empty() (vazio)
        // Isso significa: "colaborador não foi encontrado"
        // INSTRUÇÃO: Complete a linha abaixo
        // when(repository.findById(...)).thenReturn(Optional.empty());

        // PREENCHA AQUI:
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // === ACT & ASSERT ===
        // Aqui você verifica que uma exceção foi lançada
        // INSTRUÇÃO: Complete a linha abaixo
        // Você espera que service.getCollaboratorById(999L) lance uma ResourceNotFoundException
        // com a mensagem "Colaborador não encontrado"
        // Exemplo: assertThatThrownBy(() -> service.???(999L))
        //             .isInstanceOf(???.class)
        //             .hasMessageContaining("???");

        // PREENCHA AQUI:
        assertThatThrownBy(() -> service.getCollaboratorById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Colaborador não encontrado");

    }
}
