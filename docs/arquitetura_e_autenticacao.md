# Decisões Arquiteturais e Contexto Atual

## Abordagem de Autenticação e Perfis (Identidade vs. Negócio)

Para manter o sistema logístico escalável e dentro dos princípios do SRP (Single Responsibility Principle) e DRY (Don't Repeat Yourself), o projeto adota o padrão de **Separação de Identidade e Perfil** utilizando um relacionamento `1:1`.

### Como funciona a Arquitetura?
1. **Identidade Centralizada (`UserModel`)**: O controle de acesso puro (login, senha encriptada, permissões do Spring Security e emissão/verificação de tokens JWT) fica isolado no pacote de infraestrutura `auth`, sob responsabilidade do `UserModel`.
2. **Perfis de Negócio Distribuídos**: Entidades que descrevem atores específicos (ex: `AdministratorModel` e `CollaboratorModel`) encontram-se em seus próprios pacotes de domínio (`administrator`, `collaborator`), contendo apenas os dados e lógicas pertinentes às suas atividades (como salários, comissões, metas, etc).
3. **A Conexão (1:1)**: Cada entidade de perfil possui um mapeamento de banco de dados `@OneToOne` que aponta para o seu usuário (Identidade) no sistema. 

### O Que Foi Implementado Recentemente?
* **Criação do pacote `administrator`**: Desenvolvido do zero seguindo as práticas de separação em camadas (`Model`, `DTO`, `Controller`, `Service`, `Repository`).
* **Vinculação de Entidades (`UserModel` ↔ Perfis)**: Adicionado o relacionamento persistido (`@OneToOne private UserModel user;`) dentro de `AdministratorModel` e `CollaboratorModel`.
* **Refatoração da Criação de Contas (`AuthService.register()`)**:
  - O objeto de requisição (`RegisterDTO`) foi enriquecido e passou a aceitar `nome` e a `role`.
  - A lógica de transação de registro agora primeiramente gera o acesso (salva o `UserModel`) e, utilizando o atributo `role` recebido, o sistema instancia e persiste o perfil de negócio correto (`Administrator` quando for `ADMIN` e `Collaborator` quando for `USER`), amarrando os dois registros perfeitamente e devolvendo o `Token JWT`.
* **Build e Segurança**: Correção de classes obsoletas que exigiam injeção de `DaoAuthenticationProvider` no arquivo `SecurityConfig`, favorecendo o uso mais inteligente de auto-configurações baseadas em beans do Spring Boot 3+.

---

## 🚀 Próximos Passos Sugeridos (Para a próxima LLM ou Dev)

Se você assumiu o projeto agora ou está retomando contextos, as próximas tarefas essenciais que dão continuidade a essa fundação técnica são:

1. **🔒 Proteção e Autorização de Rotas de Base Baseadas em Papéis (Roles)**
   * **Ação**: Implementar anotações como `@PreAuthorize("hasRole('ADMIN')")` ou `@PreAuthorize("hasAnyRole('ADMIN', 'USER')")` nos métodos dos Controllers voltados às operações logísticas cruciais (como fretes, deleção de romaneios em pacotes como `shipment` e `freight`).
2. **✅ Validação via Testes (Unitários e Integração)**
   * **Ação**: Criar cobertura de código para o `AuthService`, cobrindo as nuances de garantir que a falha da criação de um perfil resulte em `Rollback` da criação do usuário de identidade, usando `@DataJpaTest` e Mocks adequados.
3. **🚛 Expansão dos Atores no Fluxo Logístico**
   * **Ação**: Caso o sistema evolua para permitir acesso via Painel a Motoristas (`Driver`) ou Clientes finais (`Client`), a abordagem descrita acima deve ser repetida, injetando uma nova `Role` e atrelando um `UserModel` à entidade em questão durante a criação do acesso.
4. **📖 Auditoria de Entidades e Traceability (Rastreabilidade de Ações)**
   * **Ação**: Aproveitar que a extração da identidade via token JWT foi centralizada e agora podemos obter o `ID do UserModel` autenticado por `SecurityContextHolder` para popular campos de auditoria em remessas e pedidos logísticos (ex: *"Aprovado por ID x às hh:mm"*).
