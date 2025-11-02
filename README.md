# 🐾 Pet Adoption API: Plataforma de Adoção e Gerenciamento de Pets

## ✨ Visão Geral do Projeto

A **Pet Adoption API** é o *backend* de um sistema digital destinado a facilitar o processo de adoção de animais domésticos que foram abandonados ou que necessitam de um novo lar.

Inspirado na dificuldade real de encontrar canais eficientes para ajudar pets em situação de risco, este projeto visa ser uma ferramenta essencial para **abrigos, petshops e voluntários** (os pontos de referência) que atuam na causa animal.

### 🎯 O Problema

A burocracia e a falta de coordenação entre indivíduos e organizações dificultam a rápida realocação de animais. Esta API fornece um sistema centralizado de gerenciamento de dados que permite aos **usuários autorizados** (pontos de referência) cadastrar, gerenciar e dar visibilidade aos pets disponíveis, conectando-os diretamente a novos adotantes responsáveis.

---

## 🚀 Funcionalidades Chave

O sistema é construído como uma API RESTful completa e se concentra em três módulos principais:

1.  **Autenticação e Autorização (JWT):**
    * Registro e Login de Usuários/Voluntários/Administradores.
    * Geração e validação de Tokens JWT.
    * **Permissões Customizadas:**
        * `ROLE_VOLUNTEER`: Pode cadastrar, editar e remover **apenas** os pets que ele mesmo registrou.
        * `ROLE_ADMIN`: Acesso total de gerenciamento a todos os dados do sistema.
        * `ROLE_USER`: Pode visualizar pets e registrar solicitações de adoção.
2.  **CRUD de Pets:**
    * Cadastro, visualização, edição e exclusão de pets.
    * Listagem pública de pets disponíveis para adoção.
3.  **Gerenciamento de Adoções (Futuro):**
    * Registro de solicitações de adoção.
    * Gerenciamento do status da solicitação (Pendente, Aprovada, Rejeitada).

---

## 🛠️ Stack Tecnológica (Backend)

| Categoria | Tecnologia | Justificativa |
| :--- | :--- | :--- |
| **Linguagem** | Java | Foco em robustez e desempenho. |
| **Framework** | Spring Boot 3+ | Desenvolvimento rápido e ambiente de microsserviços. |
| **Persistência** | Spring Data JPA / Hibernate | ORM para mapeamento de objetos e gerenciamento de transações. |
| **Banco de Dados** | PostgreSQL | Escolha por sua robustez, conformidade SQL e recursos avançados. |
| **Segurança** | Spring Security + JWT | Autenticação segura, autorização baseada em *roles* e API *stateless*. |
| **Build Tool** | Maven | Gerenciamento de dependências e ciclo de vida do projeto. |
| **Ambiente Dev** | Docker / Docker Compose | Isolamento e fácil inicialização do ambiente de banco de dados. |

---

## ⚙️ Configuração do Ambiente de Desenvolvimento

### Pré-requisitos
* Java Development Kit (JDK) 17+
* Maven
* Docker e Docker Compose

### Passos de Execução
1.  **Configurar o Banco de Dados (Docker):**
    * Na raiz do projeto, execute:
        ```bash
        docker-compose up -d
        ```
2.  **Configurar a Conexão:**
    * Verifique as configurações de conexão em `src/main/resources/application.properties`.
3.  **Rodar a Aplicação:**
    * Execute a aplicação Spring Boot a partir de sua IDE (IntelliJ IDEA) ou pelo terminal:
        ```bash
        ./mvnw spring-boot:run
        ```

---

## ✅ Próximos Passos (Foco Atual)

Estamos focados na implementação completa da **Fase 2: Autenticação JWT**, que inclui:

* Definição e implementação da Entidade `User` (`UserDetails`).
* Configuração do *Password Encoder*.
* Criação do Serviço `UserDetailsService` customizado.

---
