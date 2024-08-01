# Sobre

Essa aplicação foi desenvolvida com a finalidade de atender os requisitos abaixo do anexo abaixo:

A mesma é composta dos endpoints abaixo, que permite executar as seguintes operações
- Inclusão de Contas a Pagar
- Edição de Contas a Pagar(Incluindo a situação da mesma)
- Pesquisa de todos os registros de Conta a Pagar (Paginado)
- Pesquisa de registro de Contas a Pagar pelo id
- Import de Contas a Pagar por arquivo csv

## Arquitetura da aplicação

- A aplicação é funciona basicamente excutando o fluxo em 3 camadas (Controller > Service > Repository/DAO)
- Autenticação usando Basic Authentication
- Swagger
- Testes unitário utilizando JUnit e Mockito

# Desenvolvimento

## Requisitos para iniciar a aplicação para desenvolvimento

- JDK 17 ou superior
- Maven 3.6.2 ou superior
- Docker
- Git

## Como iniciar a aplicação para desenvolvimento

1. Iniciar o banco de dados Postgres executando o comando abaixo na raiz da aplicação que é onde se encontra o docker-compose.yml
   - docker-compose up
3. Iniciar a aplicação
	- Via linha de comando
	- Ou através da IDE
	
# Como iniciar a aplicação somente com o docker

Para iniciar a aplicação juntamente com o banco de dados, baixando e executando diretamente as imagens de ambos, fazer:

1. Baixar o arquivo docker-compose.yml
2. No diretório que se encontra o docker-compose.yml, executar o comando 
	- docker-compose up
3. Acessar a url do swagger para testar os endpoints
	- http://localhost:8080/swagger-ui/index.html
	
# Considerações finais

- Teste Unitários
	- Como se trata de uma aplicação exemplo, foi realizado apenas o teste unitário da classe ContasPagarService, através da classe ContasPagarServiceTest no respectivo pacote de teste de aplicação.
- Import dos arquivos
	- Como se trata de uma aplicação exemplo, deixei o import "síncrono". Em uma aplicação real, o mesmo deveria ser assíncrono e ser disponibilizado uma api para que o requisitante pudesse acompanhar o status de processamento do arquivo.
- Autenticação
	- Como se trata de uma aplicação exemplo, deixei a autenticação básica. Em uma aplicação real, temos opções melhores como Token Bearer, JWT e OAUTH.
