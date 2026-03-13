# Documentação de Autenticação e RBAC (Role-Based Access Control)

Esta documentação descreve como a autenticação e autorização funcionam no projeto Logística, detalhando os componentes implementados e como interagir com as APIs protegidas.

## 1. Visão Geral da Arquitetura

O sistema utiliza **Spring Security 6** e **JSON Web Tokens (JWT)** para garantir segurança stateless e proteção de rotas. A autenticação segue o padrão de **Separação de Identidade e Perfil**:
- A Autenticação (credenciais e Roles) é de responsabilidade da base `UserModel` (pacote `auth`).
- O domínio do negócio (ex: metas, salários) é mantido em perfis distribuídos (ex: `AdministratorModel`, `CollaboratorModel`), que mapeiam um relacionamento `1:1` com a sua identidade `UserModel`.

## 2. O que está implementado

### Componentes Chaves (`com.logistica.auth`)
* **`UserModel` e `Role`**: Entidade principal que estende `UserDetails`. A `Role` de usuário pode ser `ADMIN` ou `USER`.
* **`JwtService`**: Responsável por gerar os tokens JWT assinados com HMCA-SHA256 (usando a biblioteca `JJWT 0.12.6`). As Keys e tempos de expiração são parametrizadas no arquivo `application.properties`.
* **`JwtAuthenticationFilter`**: Interceptador (filtro) global que lê os cabeçalhos `Authorization` em busca de tokens "Bearer". Se encontrar um JWT válido e não expirado, injeta a identidade validada no `SecurityContextHolder`.
* **`AuthService`**: Contém a transação de registro, encarregada de realizar o Hash de senhas (via `BCrypt`), persistir a identidade logável (`UserModel`) e imediatamente gerar o seu Perfil correspondente (`ADMIN` grava classe Administrator, `USER` grava Collaborator).

### Restrições Baseadas em Papéis (RBAC)
As restrições atuam com base em anotações nos controladores de negócios, ativadas por `@EnableMethodSecurity`. O sistema aplica os papéis extraídos diretamente do Token JWT.
* **Leitura Geral (`GET`)**: Rotas como `/freights` e `/shipments` aceitam leitura via anotação `@PreAuthorize("hasAnyRole('ADMIN', 'USER')")`.
* **Alteração (`POST`, `PUT`, `DELETE`)**: O envio de novos Fretes, deleções, e modificações de estado logístico exigem o payload do token marcado como administrador usando `@PreAuthorize("hasRole('ADMIN')")`.

## 3. Como Rodar e Testar

### 3.1. Criar novo usuário e obter JWT
O Endpoint `/auth/register` é público. Envie a requisição para registrar a conta e o papel do novo operador.

```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
    "username": "adminUser",
    "password": "mySecurePassword123",
    "nome": "João (Administrador)",
    "role": "ADMIN"
}
```
* **Nota sobre a `role`**: Omitir ou enviar outro valor por padrão registrará o usuário como perfil de negócio padrão (`USER` / Trabalhador de base). O sistema devolve o Token JWT.

### 3.2. Fazer Login
Rota `/auth/login` (pública). Recebe o username e senha e devolve um novo JWT, caso não estejam expirados.
```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
    "username": "adminUser",
    "password": "mySecurePassword123"
}
```

### 3.3. Acessar métodos protegidos
Após garantir a string do `"token"` obtida nas requisições públicas, os próximos acessos aos endpoints protegidos (`/freights`, `/shipments`) precisam enviar a credencial no cabeçalho Header `Authorization` como uma String "Bearer".

**Para cURL, Postman ou Frontend (`fetch/axios`):**
```http
GET http://localhost:8080/freights
Authorization: Bearer <seu_token_aqui>
```
* As requisições que omitirem este cabeçalho serão retribuídas com código `403 Forbidden`.
* As requisições como um `USER` em rotas marcadas unicamente para `ADMIN` (ex: `DELETE /freights/2`) retornarão as credenciais do token como `403 Forbidden` do Spring Security (Acesso Negado local).
