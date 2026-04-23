# Trabalho Prático - Sistema de Hospedagem

## Sprint 1

### Funcionalidades
- Telas HTML (frontend)
- Cadastro de Cliente
- Cadastro de Residência
- Cadastro de Quarto
- Reserva de hospedagem
- Listagem de reservas

### Modelagem
- Diagrama de Classes
- Cartões CRC

## Estrutura
- `/frontend` — páginas HTML estáticas
- `/backend` — API REST (Spring Boot), Swagger e opcionalmente Docker
- `/diagramas`
- `/documentos`

---

## Backend (API)

API em **Spring Boot** com **MySQL**, documentação **Swagger** e dados de exemplo opcionais na primeira execução.

### Pré-requisitos
- **JDK 8** ou superior (para compilar e rodar localmente; em JDKs mais novos o projeto já fixa uma versão compatível do Lombok no `pom.xml`)
- **Maven 3.x**
- **MySQL 8** em execução, com banco e usuário configurados (veja variáveis abaixo)

### Variáveis de ambiente (conexão com o banco)

| Variável | Descrição | Valor padrão (se não informada) |
|----------|-----------|----------------------------------|
| `DB_HOST` | Host do MySQL | `localhost` |
| `DB_PORT` | Porta do MySQL | `3306` |
| `DB_NAME` | Nome do banco de dados | `hospedagem` |
| `MYSQL_USER` | Usuário JDBC | `root` |
| `MYSQL_USER_PASSWORD` | Senha do usuário JDBC | `root` |

Crie o banco antes da primeira subida, por exemplo:

```sql
CREATE DATABASE IF NOT EXISTS hospedagem CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Executar localmente (sem Docker)

No diretório `backend`:

```bash
cd backend
mvn spring-boot:run
```

A API sobe em **http://localhost:8080** (porta configurável em `application.properties`).

**Testes e build:**

```bash
cd backend
mvn test              # testes unitários / de integração (quando existirem)
mvn package -DskipTests   # gera o JAR em target/
```

**Importante:** o comando correto é `spring-boot:run` (não `sprint-boot`). Evite `sudo mvn`, para não misturar permissões da pasta `target/` e do cache do Maven.

### Docker Compose (API + MySQL)

No diretório `backend`:

```bash
cd backend
docker compose up --build
```

O serviço `web` aguarda o MySQL ficar saudável antes de iniciar. Credenciais padrão do compose estão em `docker-compose.yml` (usuário `hospedagem-user`, senha `changeit`, banco `hospedagem`).

### Documentação Swagger (OpenAPI 3 via Springfox)

Com a aplicação no ar:

- **Interface Swagger UI:** http://localhost:8080/swagger-ui/index.html  
- **Especificação JSON:** http://localhost:8080/v3/api-docs  

Os endpoints REST seguem os caminhos dos controllers (ex.: `/clientes`, `/residencias`, `/quartos`, `/alugueis`).

### Health check (Actuator)

- **http://localhost:8080/actuator/health**

### Dados de exemplo (seed)

Na primeira execução com banco **sem residências**, a API pode inserir clientes, uma residência, quartos e aluguéis de demonstração (idempotente: não duplica se já existir residência).

- Para **desativar** o seed: defina `SEED_DATA=false` (ou `app.seed-data=false` conforme `application.properties`).

### Problemas comuns

- **Erro ao criar beans JPA / `JdbcEnvironment`:** em geral o MySQL não está acessível (serviço parado, host/porta errados, banco inexistente ou credenciais incorretas). Confira a primeira causa raiz (`Caused by`) no log, costuma ser `CommunicationsException` ou `SQLException`.
- **Build com Lombok em JDK recente:** o `pom.xml` do backend define uma versão de Lombok compatível com JDKs modernos.
