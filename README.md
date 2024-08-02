# Sobre

Essa aplicação foi desenvolvida com a finalidade de atender os requisitos descritos no [arquivo](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/outros/teste%20-%20JAVA%20Sr.pdf).

A mesma é composta dos endpoints abaixo, que permite executar as seguintes operações
- Inclusão de Contas a Pagar
- Edição de Contas a Pagar(Incluindo a situação da mesma)
- Pesquisa de todos os registros de Conta a Pagar (Paginado)
- Pesquisa de registro de Contas a Pagar pelo id
- Import de Contas a Pagar por arquivo csv

## Arquitetura da aplicação

- A aplicação funciona basicamente excutando o fluxo em 3 camadas (Controller > Service > Repository/DAO)
- Autenticação usando Basic Authentication
- Swagger
- Testes unitário utilizando JUnit e Mockito

## Organização do código do Projeto

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/008.jpg)

# Como iniciar e testar a aplicação somente com o docker

Para iniciar a aplicação juntamente com o banco de dados, baixando e executando diretamente as imagens de ambos, fazer:

1. Baixar o arquivo [docker-compose.yml](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/outros/docker-compose.yml)
2. No diretório que se encontra o docker-compose.yml, executar o comando 
	- docker-compose up
3. Acessar a url do swagger para testar os endpoints
	- http://localhost:8080/swagger-ui/index.html

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/001.jpg)

4. Necessário se autenticar, clicando no botão Authorize do Swagger

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/004.jpg)

4. Informar usuario **"user"** e senha **"password"** e clicar no botão **Authorize**

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/002.jpg)

6. Clicar no Botão Close.

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/003.jpg)

8. A partir de agora toda e qualquer requisição terá o header **Authorization**

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/005.jpg)

# Como testar o import de contas a pagar pelo arquivo csv

1. Baixar o [arquivo CSV](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/outros/contas-pagar.csv)
2. Após estar autenticado, importar o arquivo csv baixado conforme abaixo e clicar em **"Executar"**

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/006.jpg)

3. Após ter processado o arquivo, o endpoint retorna status 200 e a listagem dos registros que foram importados e persistidos na base de dados. 

![image](https://github.com/carloshfmaciel/ms-contas-pagar/blob/master/screenshots/007.jpg)

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
	
# Considerações finais

- Teste Unitários
	- Como se trata de uma aplicação exemplo, foi realizado apenas o teste unitário da classe ContasPagarService, através da classe ContasPagarServiceTest no respectivo pacote de teste de aplicação.
- Import dos arquivos
	- Como se trata de uma aplicação exemplo, deixei o import "síncrono". Em uma aplicação real, o mesmo deveria ser assíncrono e ser disponibilizado uma api para que o requisitante pudesse acompanhar o status de processamento do arquivo.
- Autenticação
	- Como se trata de uma aplicação exemplo, deixei a autenticação básica. Em uma aplicação real, temos opções melhores como Token Bearer, JWT e OAUTH.
 - Credenciais de banco de dados(application.yml)
       - Como se trata de uma aplicação exemplo, copiei o application.properties diretamente para dentro da imagem docker da aplicação. Em uma aplicação real, essas credenciais deveriam ser informadas via variável de ambiente através de algum mecanismo de CI/CD obtendo os dados diretamente de um Secret Manager.  	
