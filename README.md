# Tech Challenge

## MicroServiço de API PAYMENT - Responsável por Pagament

Este é um projeto desenvolvido como parte do MBA da FIAP. Trata-se de uma aplicação Java para gerenciamento de pedidos,
utilizando Postgres para persistência de dados e Maven para gerenciamento de dependências.

## Sobre o Projeto

O projeto é uma aplicação de pedidos, onde os usuários podem criar, atualizar e excluir pedidos. Cada pedido pode conter
vários itens, e cada item está associado a um pedido.

## Coverage

![img.png](img.png)

## Coberta de Testes

A cobertura de testes do projeto é de 80%. Utilizando SonarQube para análise de código e cobertura de testes.

## Branches

O projeto possui duas branches principais:
- **main**: contém o código de produção.
- **develop**: contém o código em desenvolvimento.

### Video de apresentação da arquitetura do projeto

[![Video de Apresentação da Arquitetura](https://img.youtube.com/vi/7pZ2tByl9t8/0.jpg)](https://www.youtube.com/watch?v=7pZ2tByl9t8)

### Diagrama de Arquitetura

Abaixo está o diagrama de arquitetura do projeto, que ilustra a estrutura e os componentes principais da aplicação:

![Diagrama de Arquitetura](image/tech_challenge_architecture.svg)

### Tecnologias Utilizadas

- **Java 21**
- **Maven 3.8.6**
- **Spring Boot**
- **Hibernate**
- **ModelMapper**
- **Docker**
- **Kubernetes**
- **Postgres**

## Como Rodar o Projeto

O projeto está configurado para ser executado em um container Docker. Para executar o projeto, siga os passos abaixo:

1. **Clone o repositório** para o seu sistema local:

```bash
  git clone https://github.com/JoseVitorDurante/api-food.git
```

2. **Navegue até o diretório do projeto:**:

```bash
  cd api-food
```

3. **Execute o comando para subir o container Docker**:

```bash
  docker compose up --build --force-recreate
```

Alternativamente, para execução com Kubernetes:

```bash
  kubectl apply -f ./k8s
```

Para mais detalhes acesse o arquivo README.md na pasta k8s. [README.md](k8s%2FREADME.md)

### Expondo a Aplicação com Ngrok

Para expor a aplicação para a internet e realizar testes externos, utilize o Ngrok:

```bash
choco install ngrok
```

```bash
ngrok config add-authtoken <your-token>
```

```bash
ngrok http http://localhost:8080
```

depois de rodar os comandos acima, copie o link gerado pelo ngrok informe no aplication.yaml a url gerada pelo ngrok ou
utilize o endPoint de update url do mercado pago

curl:

```bash
curl -X 'PUT' \
  'http://localhost:8088/config/mercado-pago-url' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '"https://sua-url-base.com"'
  ```

## Documentação da API

A documentação da API está disponível no Swagger, e pode ser acessada através do link:

```
  http://localhost:8088/swagger-ui.html
```

## Dados de Teste

O projeto inclui dados de teste pré-carregados para facilitar as operações. Todas as entidades, exceto pedidos, possuem
dados de exemplo.

## Teste de Estresse com K6

Para realizar testes de estresse na aplicação, utilizamos o K6. Siga os passos abaixo para executar o teste:

Instale o K6 no seu sistema. Para instruções detalhadas, visite a documentação oficial do K6.

### Executando o Teste de Estresse

```bash
  k6 run load-load-test.js
```

**Observação:**
Certifique-se de que todos os pré-requisitos estão instalados e configurados corretamente antes de iniciar o projeto.
Para qualquer dúvida ou problema, consulte a documentação oficial das ferramentas utilizadas ou entre em contato com o
responsável pelo projeto.