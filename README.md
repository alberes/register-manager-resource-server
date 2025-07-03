


# Projeto: Servidor de autorização
Este projeto é responsável por autorizar e gerar tokens Client Credencials e Autorization Code.

Funcionalidades
* Emissão de tokens OAuth2:    
  Gera tokens de acesso (access tokens) e refresh tokens para clientes autorizados.
* Suporte a múltiplos grant types:    
  Authorization Code, Client Credentials, Refresh Token, e outros.
* OpenID Connect (OIDC):    
  Suporte a autenticação de usuários e emissão de ID Tokens.
* Registro e gerenciamento de clientes:    
  Permite configurar clientes OAuth2 com diferentes permissões, escopos e redirecionamentos.    
  Endpoints padrão OAuth2/OIDC:    
  /oauth2/authorize (autorização)    
  /oauth2/token (emissão de token)    
  /oauth2/jwks (exposição da chave pública para validação de JWT)    
  /userinfo (informações do usuário, OIDC)
* Assinatura de tokens JWT:    
  Tokens podem ser assinados com chaves simétricas ou assimétricas (RSA/ECDSA).
* Customização de fluxos de autenticação:    
  Permite customizar autenticação, consentimento, claims, etc.
* Integração com Spring Security:    
  Usa toda a infraestrutura de autenticação, autorização e usuários do Spring Security.

### Estrutura do Banco de Dados
* Fluxo Client Credencials    
  O projeto utiliza uma tabela chamada client para o fluxo client credencials. A seguinte restrição foi adicionada para garantir a unicidade do campo de client_id:    
  ALTER TABLE IF exists client ADD CONSTRAINT client_client_id UNIQUE (client_id);
* Fluxo Authorization Code    
  O projeto utiliza uma tabela chamada user_account para o fluxo authorization code. A seguinte restrição foi adicionada para garantir a unicidade do campo de email:    
  ALTER TABLE IF exists user_account ADD CONSTRAINT user_account_email UNIQUE (email);

### Tecnologias Utilizadas
* Linguagem: Java e SQL
* Banco de Dados: [Postgres](https://www.postgresql.org/)
* Frameworks: [Spring Boot 3.5.0](https://start.spring.io/)
* Dependencias: (Spring Security)[https://docs.spring.io/spring-security/reference/index.html],
* Dependencias: (spring Oauth2 Authorization Server)[https://docs.spring.io/spring-authorization-server/reference/protocol-endpoints.html] 
* (JPA)[https://docs.spring.io/spring-data/jpa/reference/index.html],
* (lombok)[https://projectlombok.org/features/],
* (mapstruct)[https://mapstruct.org/documentation/], (jackson)[https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-core/latest/index.html],
* (OpenApi)[https://springdoc.org/] 
* (actuator)[https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/] * JDK: 21
* IDE: [Intellij](https://www.jetbrains.com/idea/)
* Gerenciado de dependencias: [Apache Maven 3.9.9](https://maven.apache.org/)
* Container: [Docker](https://www.docker.com/) e [Docker Hub](https://hub.docker.com/)
* Ferramentas: [Postman](https://www.postman.com/) [Google Chrome    
  Versão 136.0.7103.93 (Versão oficial) 64 bits](https://www.google.com/intl/pt-BR/chrome/), [PGAdmin](https://www.pgadmin.org/), [Beekeeper](https://www.beekeeperstudio.io/)

### Como Executar
1. Clone o repositório: git clone https://github.com/alberes/register-manager-authorization-server
2. Configure o banco de dados:
- Banco de dados: register_manager
- Tabelas: client, user_account, user_account_role    
  Certifique-se de que o banco de dados está configurado corretamente.    
  A aplicação irá criar as tabelas automaticamento caso não exista ou execute o script antes que se encontra no projeto.    
  Localizar os arquivos [SUB_DIRETORIOS]/register-manager-authorization-server/DDL.sql e [SUB_DIRETORIOS]/register-manager-authorization-server/DML.sql
3. Usando uma imagem Docker (Opcional)
4. Tempo de sessão está configurado para 60 minutos

Um opção é criar um container docker com a imagem do Postgres, abaixo um exemplo que configurar usuário, senha e cria o banco de dados.  
``` docker run --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES=postgres -e POSTGRES_DB=register_manager -d postgres:16.3 ``` 4. Executar o projeto
- Abrir o terminal na raiz do projeto [SUB_DIRETORIOS]/register-manager-authorization-server e exeuctar o comando abaixo para gerar o pacote.  
  ``` mvn -DskipTests=true clean package ``` - No termial entrar no diretório [SUB_DIRETORIOS]/register-manager-authorization-server/target  
  ``` java -jar register-manager-authorization-server-0.0.1-SNAPSHOT.jar ``` A aplicação subirá na porta 9090

### Testes
1. Carga inicial:
    - Usar o arquivo DML.sql
        - [SUB_DIRETORIOS]/register-manager-authorization-server/DML.sql
2. Observabilidade e métricas
    - (Monitoramento)[http://localhost:9091/actuator] - (Log)[http://localhost:9091/actuator/logfile] - (Metricas)[http://localhost:9091/actuator/metrics] - (DataSource)[http://localhost:9091/actuator/metrics/hikaricp.connections.active] - (Memória)[http://localhost:9091/actuator/metrics/jvm.buffer.memory.used] - (CPU)[http://localhost:9091/actuator/metrics/process.cpu.usage] - (Autorzação)[http://localhost:9091/actuator/metrics/spring.security.authorizations.active] - (Sessões)[tomcat.sessions.active.current]
3. Testes usando Postman
    - Localize a collection que se encontra no diretório [SUB_DIRETORIOS]/register-manager-authorization-server/register-manager-authorization-server.postman_collection
    - Importar no Postman

### Autenticações

1 - Client Credencials

### Autenticações

1 - Client Credencials (client-id: my-client-id, client-secret: my-client-secret e scope: USER)

curl --location 'http://localhost:9090/oauth2/token' \    --header 'Authorization: Basic bXktY2xpZW50LWlkOm15LWNsaWVudC1zZWNyZXQ=' \      
--header 'Content-Type: application/x-www-form-urlencoded' \      
--data-urlencode 'grant_type=client_credentials' \      
--data-urlencode 'scope=USER'

```mermaid sequenceDiagram  
Client->> AuthorizationServer: POST http://localhost:9090/oauth2/token AuthorizationServer->>RegisteredClientRepository: findByClientId(clientId)  
RegisteredClientRepository-->>AuthorizationServer:RegisteredClient  
AuthorizationServer-->>AuthorizationServer: validationClientSecret  
AuthorizationServer-->>Client: Token JWT  
```  

2 - Authorization Code (username: admin@admin.com e password: admin123456)

```mermaid sequenceDiagram  
Client->> AuthorizationServer: GET http://localhost:9090/oauth2/authorize?response_type=code&client_id=my-client-id&redirect_uri=http://localhost:8081  
AuthorizationServer-->>Client: Location: http://localhost:9090/login  
Client->>AuthorizationServer: GET http://localhost:9090/login  
AuthorizationServer-->>Client: Form (User/Password)  
Client->>AuthorizationServer: POST http://localhost:9090/login  
AuthorizationServer->>AuthenticationProvider: DaoAuthenticationProvider  
AuthenticationProvider->>UserDetailsService: loadUserByUsername(username)  
UserDetailsService->>UserDetailsService: validationUsername  
UserDetailsService-->>AuthenticationProvider: UserDetails  
AuthenticationProvider->>AuthenticationProvider: validation password  
AuthenticationProvider-->>AuthorizationServer: UserDetails  
AuthorizationServer-->>Client: Location: http://localhost:9090/oauth2/authorize?response_type=code&client_id=my-client-id&redirect_uri=http://localhost:8081&continue  
Client->>AuthorizationServer: GET http://localhost:9090/oauth2/authorize?response_type=code&client_id=my-client-id&redirect_uri=http://localhost:8081&continue  
AuthorizationServer-->>Client: Location: http://localhost:8081?code=gG0LHNbg_F5S8DfSCM89KLVblCKW2DgNl0w-hXaoVxpl5De3qByp5pQCUlYQXT6UBjhOd-OVSPtjWMZUtH9GMHOXpZzBeb2JMCPcknPRU8p79oPv3VP__RxuuIvmJsBY  
Client->>AuthorizationServer: POST http://localhost:9090/oauth2/token  
AuthorizationServer-->>Client: JWT access_token  
```

### Docker

1. Montando um ambiente Docker
   <a id="criar-register-manager-authorization-server"></a>
    - Criando uma imagem da aplicação
      docker build --tag register-manager-authorization-server:1.0.0 .
    - Criando uma rede para comunicação entre os containeres
      docker network create register-manager-authorization-network
    - Subindo um container Docker com banco de dados Postgres
      docker run --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES=postgres -e POSTGRES_DB=register_manager --network register-manager-authorization-network -d postgres:16.3
    - Subindo um container Docker da aplicação register-manager-authorization-server
      docker run --name register-manager-authorization-server -p 9090:9090 -p 9091:9091 --network register-manager-authorization-network -d register-manager-authorization-server:1.0.0
        - ou use as veriáveis de ambiente
          docker run --name register-manager-authorization-server -p 9090:9090 -p 9091:9091 --network register-manager-authorization-network -e DATASOURCE_URL=jdbc:postgresql://postgresdb:5432/register_manager -e DATASOURCE_USERNAME=postgres -e DATASOURCE_PASSWORD=postgres -e SHOW_SQL=true -e DDL_AUTO=update -e FORMAT_SQL=true -e APP_PORT=9090 -e MONITORING_PORT=9091 -e MONITORING_LIST=* -e LOG_NAME=register-manager-authorization-server.log -e LOG_LEVEL=warn -e ACCESS_TOKEN_EXPIRATION=30 -e REFRESH_TOKEN_EXPIRATION=60 -d register-manager-authorization-server:1.0.0
    - Listando os containeres que estão no ar
      docker ps
    - Listando as imagens
      docker images
    - Parando o contaner
      docker stop register-manager-authorization-server
    - Subindo um container já existente
      docker start register-manager-authorization-server
3. Demontando o ambiente (Pare os containeres da aplicação e banco de dados antes de executar os comandos abaixo)
    - Removendo a aplicação
      docker rm register-manager-authorization-server
    - Removendo o banco de dados
      docker rm postgresdb
    - Removendo a rede
      docker network rm register-manager-authorization-network
    - Removendo a imagem da aplicação
      docker rmi register-manager-authorization-server:1.0.0
    - Removendo a imagem do banco de dados
      docker rmi postgres:16.3
   ### Docker Compose para gerenciar os containeres
    - Com a imagem da aplicação [register-manager-authorization-server](#criar-register-manager-authorization-server) é só executar o comando abaixo:
      docker-compose up -d
    - Parando a aplicação com docker compose
      docker-compose down