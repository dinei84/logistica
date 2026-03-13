# Documentação de Testes - Logística

Este documento descreve as abordagens e as mecânicas que asseguram a estabilidade da aplicação em relação às lógicas cruciais do sistema, especialmente transações que modificam diferentes tabelas com base em perfis de negócios.

## 1. Visão Geral

Até o momento, nossos esforços garantem a resiliência do pacote de autorização (`com.logistica.auth`). Toda vez que há alteração na lógica de criação de logins (`AuthService`), precisamos assegurar que a Identidade Logável (`UserModel`) e seu Perfil de Negócio Vinculado (`Administrator` ou `Collaborator`) estejam integralmente alinhados antes de finalizar a transação no banco.

Os testes foram construídos na biblioteca utilitária em Java **JUnit 5**, mesclando Mocks com isolamento extremo (`Mockito`) com testes em contexto do Spring inteiro gerando simulações no banco **H2**.

---

## 2. O Que Está Implementado

### 2.1 Testes Unitários com Mockito (`AuthServiceTest.java`)
Garantem a validação estrita da lógica da classe de serviço independente da infraestrutura de banco de dados e APIs externas:
* **`register_ShouldSaveAdministrator_WhenRoleIsAdmin`**: Valida se ao acionar o serviço com o Role `ADMIN`, os _Mocks_ injetados provam que o método `save()` do repositório `AdministratorRepository` foi chamado, omitindo o `CollaboratorRepository`.
* **`register_ShouldSaveCollaborator_WhenRoleIsUser`**: Validade reversa se perante uma request default (`USER`), o repositório correto de `CollaboratorRepository` é disparado.
* **`register_ShouldThrowBadRequest_WhenUsernameAlreadyExists`**: Valida e impede o fluxo prematuramente caso o Username já exista, conferindo uma interrupção antes que qualquer save() seja acionado na pipeline. 

### 2.2 Testes de Integração com Spring Boot e H2 (`AuthServiceIntegrationTest.java`)
Garantem a estabilidade transacional e o Rollback das persistências no ecossistema final:
* **`register_ShouldRollbackUserModel_WhenProfileSaveFails`**: Prova em tempo de execução o benefício da anotação `@Transactional` no serviço de Registro. 
  * O teste deliberadamente burla a classe `CollaboratorRepository` com um `@MockitoBean` para lançar uma exceção de Falha na Gravação.
  * O comando final inspeciona o banco H2 local tentando buscar o usuário original que começou o processo `UserModel`. Devido ao rollback, esse usuário não deve existir e o comando `Optional.empty()` deve ser verdadeiro. Dessa forma, é possível provar que a aplicação não "vaza" usuários sem perfis definidos gerando dor de cabeça futura.

---

## 3. Como Rodar e Adicionar Futuras Implementações

### Rodar a Bateria de Testes
Estando no root do diretório que detém o script `mvnw` local e o arquivo base `pom.xml`, pode digitar no terminal (Powershell e Cmd) ou via Scripts das IDEs (ex: IntelliJ):

**Para rodar todos os testes na aplicação:**
```powershell
.\mvnw.cmd test
```

**Para rodar classes e/ou pacotes específicos:**
```powershell
.\mvnw.cmd test -Dtest=AuthServiceTest,AuthServiceIntegrationTest
```

### Criar Fluxos para Futuras Features

A partir do momento em que for feita a "Expansão de Atores no Fluxo Logístico" orientada pela documentação de arquitetura, como a inserção de `Motorista` (Driver):
1. Será acrescida uma `Role.DRIVER`.
2. O `AuthService` será atualizado com uma ramificação no `if/else` para construir a sua entidade `DriverModel` que extenda os métodos de rotinas dos caminhões e sua identidade Logável (relacionamento OneToOne de `UserModel`).
3. Imeditamente vá a suíte de unitários `AuthServiceTest.java` e adicione a nova classe nos atalhos `@Mock` injetados. Verifique em um novo cenário `@Test` se a pipeline de salvamento funcionou pra ela.
4. Adicione essa validação à esteira global da aplicação.
