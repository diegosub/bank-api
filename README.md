<h1 align="center">BANK-API</h1>
<p align="center">API de Gerenciamento de contas bancárias</p>


### Features

- [x] Para abrir uma conta é necessário apenas o nome completo e CPF da pessoa, mas só é permitido uma conta por pessoa;
- [x] Com essa conta é possível realizar transferências para outras contas e depositar;
- [x] Não aceitamos valores negativos nas contas;
- [x] Por questão de segurança cada transação de depósito não pode ser maior do que R$2.000;
- [x] As transferências entre contas são gratuitas e ilimitadas;


### Pré-requisitos

Para rodar a api será necessário ter instalado na máquina o docker e docker-compose

### 🎲 Buildando e Executando a API

```bash
# Clone este repositório
$ git clone https://github.com/diegosub/bank-api.git

# Abra o terminal na pasta raiz do projeto

# Execute o comando: 
      docker-compose up -d bank-postgres
      # Este comando irá subir o ambiente postgres na máquina e logo após iremos gerar a imagem do projeto.

# Gerando a imagem do projeto:
      ./mvnw clean package -Pdocker
      # Ao buildar o projeto, o maven irá rodar todos os testes de api e de integração. 
      
# Subindo a API via docker-compose:
      docker-compose up -d
      # Finalmente o docker compose irá executar todos os serviços necessários para disponibilizar a API
      

```

### Sobre a implementação

O projeto foi implementado utilizando a linguagem JAVA, com o framework Spring (SpringBoot, Spring Mvc, Spring Data);
O banco de dados utilizado foi o postgresql;
Os testes de API e integração foi implementado com o JUnit 5 com o MockMvc;
O Docker e Docker Compose foram configurados para a api subir atravéz de seus containers específicos;
A IDE utilizada para implementar o projeto foi o STS (Spring Tool Suite 4).

O projeto está dividido em 2 camadas principais:

  API: Responsável pela camada de controller com os endpoints, autenticação, 
  configurações de segurança via @Bean e middlewares de validação do token, 
  dtos e middlewares para tratamento de exceções.  
  DOMAIN: Camada de Models, Services, Repositórios e Exceptions personalizadas.
    















