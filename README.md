


# Projeto: Servidor de autorização
Este projeto é responsável por cadastro de usuários e clientes (ClientId e ClientSecret) e trabalha junto com authorizarion server.

Funcionalidades
* Cadastro de usuário:    
  Cadastro, consulta de lista de usuários, consulta simples, atualização e exclusão.
* Cadastro de clientes (ClientId e ClientSecret)
  Cadastro, consulta de lista de clientes, consulta simples, atualização e exclusão.
* Suporte a múltiplos grant types:    
  Authorization Code, Client Credentials, Refresh Token, e outros.

### Estrutura do Banco de Dados
* Fluxo Client Credencials    
  O projeto utiliza uma tabela chamada client para o fluxo client credencials. A seguinte restrição foi adicionada para garantir a unicidade do campo de client_id:    
  ALTER TABLE IF exists client ADD CONSTRAINT client_client_id UNIQUE (client_id);
* Fluxo Authorization Code    
  O projeto utiliza uma tabela chamada user_account para o fluxo authorization code. A seguinte restrição foi adicionada para garantir a unicidade do campo de email:    
  ALTER TABLE IF exists user_account ADD CONSTRAINT user_account_email UNIQUE (email);

### Tecnologias Utilizadas
* Linguagem:
  * Java e SQL
* Banco de Dados: [Postgres](https://www.postgresql.org/)
  * Frameworks:
    * [Spring Boot 3.5.0](https://start.spring.io/)
  * Dependencias:
    * [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
    * [Spring Oauth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
    * [JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
    * [Lombok](https://projectlombok.org/features/)
    * [Mapstruct](https://mapstruct.org/documentation/)
    * [OpenApi](https://springdoc.org/)
    * [actuator](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/)
* JDK:
  * versão 17
* IDE:
  * [Intellij](https://www.jetbrains.com/idea/)
* Gerenciado de dependencias:
  * [Apache Maven 3.9.9](https://maven.apache.org/)
* Container:
  * [Docker](https://www.docker.com/)
  * [Docker Hub](https://hub.docker.com/)
* Ferramentas:
  * [Postman](https://www.postman.com/)
  * [Google Chrome Versão 136.0.7103.93 Versão oficial 64 bits](https://www.google.com/intl/pt-BR/chrome/)
  * [PGAdmin](https://www.pgadmin.org/)
  * [Beekeeper](https://www.beekeeperstudio.io/)
* Ferramentas auxiliares:
  * [Markdown](https://stackedit.io/app#)
  * [Criar diagramas](https://docs.github.com/pt/get-started/writing-on-github/working-with-advanced-formatting/creating-diagrams)
  * [JWT IO](https://jwt.io/)
  * [Criptografia online](https://bcrypt-generator.com/)
  * [Base 64](https://www.base64encode.org/)
  * [4DEV](https://www.4devs.com.br/)

### Como Executar
1. Clone o repositório: git clone https://github.com/alberes/register-manager-resource-server
2. Configure o banco de dados:
- Banco de dados: register_manager
- Tabelas: client, user_account, user_account_role e user_account_scope    
  Certifique-se de que o banco de dados está configurado corretamente.    
  A aplicação irá criar as tabelas automaticamento caso não exista ou execute o script antes que se encontra no projeto.    
  Localizar os arquivos [SUB_DIRETORIOS]/register-manager-resource-server/DDL.sql e [SUB_DIRETORIOS]/register-manager-resource-server/DML.sql
3. Usando uma imagem Docker (Opcional)
4. Tempo de sessão está configurado para 60 minutos

Um opção é criar um container docker com a imagem do Postgres, abaixo um exemplo que configurar usuário, senha e cria o banco de dados.  
```
docker run --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES=postgres -e POSTGRES_DB=register_manager -d postgres:16.3
```
4. Executar o projeto
- Abrir o terminal na raiz do projeto [SUB_DIRETORIOS]/register-manager-resource-server e exeuctar o comando abaixo para gerar o pacote.  
```
mvn -DskipTests=true clean package
```
- No termial entrar no diretório [SUB_DIRETORIOS]/register-manager-resource-server/target  
```
java -jar register-manager-resource-server-0.0.1-SNAPSHOT.jar
```

A aplicação subirá na porta 9080

### Testes
1. Carga inicial:
    - Usar o arquivo DML.sql
        - [SUB_DIRETORIOS]/register-manager-resource-server/DML.sql
2. Observabilidade e métricas
    - (Monitoramento)[http://localhost:9081/actuator]
    - (Log)[http://localhost:9081/actuator/logfile]
    - (Metricas)[http://localhost:9081/actuator/metrics]
    - (DataSource)[http://localhost:9081/actuator/metrics/hikaricp.connections.active]
    - (Memória)[http://localhost:9081/actuator/metrics/jvm.buffer.memory.used]
    - (CPU)[http://localhost:9081/actuator/metrics/process.cpu.usage]
    - (Autorzação)[http://localhost:9081/actuator/metrics/spring.security.authorizations.active]
    - (Sessões)[tomcat.sessions.active.current]
3. Testes usando Postman
    - Localize a collection que se encontra no diretório [SUB_DIRETORIOS]/register-manager-resource-server/register-manager-resource-server.postman_collection
    - Importar no Postman

### Autenticações

(Authorization Server)[https://github.com/alberes/register-manager-authorization-server/blob/master/README.md]

### Docker

1. Montando um ambiente Docker (alterar a variável de ambiente active para docker no arquivo application.yaml)
   <a id="criar-register-manager-resource-server"></a>
    - Criando uma imagem da aplicação
      docker build --tag register-manager-resource-server:1.0.0 .
    - Criando uma rede para comunicação entre os containeres
      docker network create register-manager-authorization-network
    - Subindo um container Docker com banco de dados Postgres
      docker run --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES=postgres -e POSTGRES_DB=register_manager --network register-manager-authorization-network -d postgres:16.3
    - Subindo um container Docker da aplicação register-manager-resource-server
      docker run --name register-manager-resource-server -p 9080:9080 -p 9081:9081 --network register-manager-authorization-network -d register-manager-resource-server:1.0.0
        - ou use as veriáveis de ambiente
          docker run --name register-manager-resource-server -p 9080:9080 -p 9081:9081 --network register-manager-authorization-network -e DATASOURCE_URL=jdbc:postgresql://postgresdb:5432/register_manager -e DATASOURCE_USERNAME=postgres -e DATASOURCE_PASSWORD=postgres -e SHOW_SQL=true -e DDL_AUTO=update -e FORMAT_SQL=true -e APP_PORT=9080 -e MONITORING_PORT=9081 -e MONITORING_LIST=* -e LOG_NAME=register-manager-resource-server.log -e LOG_LEVEL=warn -d register-manager-resource-server:1.0.0
    - Listando os containeres que estão no ar
      docker ps
    - Listando as imagens
      docker images
    - Parando o contaner
      docker stop register-manager-resource-server
    - Subindo um container já existente
      docker start register-manager-resource-server
3. Demontando o ambiente (Pare os containeres da aplicação e banco de dados antes de executar os comandos abaixo)
    - Removendo a aplicação
      docker rm register-manager-resource-server
    - Removendo o banco de dados
      docker rm postgresdb
    - Removendo a rede
      docker network rm register-manager-authorization-network
    - Removendo a imagem da aplicação
      docker rmi register-manager-resource-server:1.0.0
    - Removendo a imagem do banco de dados
      docker rmi postgres:16.3
   ### Docker Compose para gerenciar os containeres
    - Com a imagem da aplicação [register-manager-resource-server](#criar-register-manager-resource-server) é só executar o comando abaixo:
      docker-compose up -d
    - Parando a aplicação com docker compose
      docker-compose down