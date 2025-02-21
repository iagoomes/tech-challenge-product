# Product Management API

## Descrição

Esta API permite o gerenciamento de produtos, incluindo operações de CRUD e processamento de arquivos CSV para inserção em lote. Utiliza Spring Batch para processamento eficiente e OpenAPI para documentação automatizada.

## Tecnologias Utilizadas

- **Spring Boot 3.3.3**: Framework para o desenvolvimento da aplicação.
- **Java 21**: Linguagem de programação utilizada.
- **Spring Batch**: Para processamento de dados em lote.
- **OpenAPI**: Para a documentação e geração automática de código.
- **Lombok**: Para reduzir o código boilerplate (getters, setters, construtores).
- **H2**: Banco de dados em memória para armazenamento das informações.

## Pré-requisitos

- **JDK 21** ou superior.
- **Maven**: Para o gerenciamento de dependências e build.

## Instalação

1. **Clonar o Repositório:**

```bash
  git clone https://github.com/iagoomes/tech-challenge-product.git
```

### 🚀 Opções de Build e Execução

Abaixo seguem duas opções de build e execução da aplicação: Dockerfile e Maven.

## Build com Docker
Para construir a aplicação utilizando o Docker, execute o seguinte comando no diretório do projeto:

```bash
  docker build -t product-management-app .
```

Após a construção da imagem, você pode executar a aplicação com o comando:

```bash
  docker run -p 8080:8080 product-management-app
```

## Gerando os Recursos com o OpenAPI Generator

O OpenAPI Generator automatiza a geração de código com base na especificação OpenAPI. Benefícios incluem:

- **Redução de Erros**: O código gerado segue rigorosamente a especificação.
- **Aceleração do Desenvolvimento**: Controladores e modelos são gerados automaticamente.
- **Facilidade de Manutenção**: Atualizações na especificação OpenAPI são rapidamente refletidas no código.

2. Gerar os Controladores e Modelos:

Para gerar automaticamente as classes necessárias, execute o seguinte comando Maven:

```bash
  mvn clean generate-sources
```

Isso criará os controladores, modelos e outros recursos com base na especificação `openapi.yaml` localizada em `src/main/resources/`.

## Executando a Aplicação

3. Compilar o Projeto:

```bash
   mvn clean install
```

4. Executar a Aplicação:

```bash
   mvn spring-boot:run
```

A aplicação estará disponível em http://localhost:8080.

## Endpoints

### Gerenciamento de Produtos

**GET** _/products_: Retorna todos os produtos cadastrados.
- **200 OK**: Lista de produtos.
- **500 Internal Server Error**: Erro interno do servidor.

**POST** _/products_: Cria um novo produto.
- **Body**: Objeto `ProductApiModel`.
- **201 Created**: Produto criado com sucesso.
- **400 Bad Request**: Requisição inválida.
- **500 Internal Server Error**: Erro interno do servidor.

**GET** _/products/{id}_: Retorna um produto pelo ID.
- **Parâmetro**: `id` (integer, obrigatório)
- **200 OK**: Objeto `ProductApiModel`.
- **404 Not Found**: Produto não encontrado.
- **500 Internal Server Error**: Erro interno do servidor.

**PUT** _/products/{id}_: Atualiza um produto pelo ID.
- **Parâmetros**:
    - **Path**: `id` (integer, obrigatório)
    - **Body**: Objeto `ProductApiModel`.
- **200 OK**: Produto atualizado com sucesso.
- **400 Bad Request**: Requisição inválida.
- **404 Not Found**: Produto não encontrado.
- **500 Internal Server Error**: Erro interno do servidor.

**DELETE** _/products/{id}_: Remove um produto.
- **Parâmetro**: `id` (integer, obrigatório)
- **204 No Content**: Produto removido com sucesso.
- **404 Not Found**: Produto não encontrado.
- **500 Internal Server Error**: Erro interno do servidor.

### Upload de Arquivo CSV

**POST** _/product-uploads_
- **Parâmetro**: Arquivo CSV contendo os produtos.
- **201 Created**: Produtos criados com sucesso.
- **400 Bad Request**: Arquivo inválido.
- **500 Internal Server Error**: Erro interno do servidor.

## Execução do Job Batch

**POST** _/product-uploads_
- **Descrição**: Adiciona novos produtos ao sistema a partir de um arquivo CSV.
- **201 Created**: Produtos criados com sucesso.
- **400 Bad Request**: Arquivo inválido.
- **500 Internal Server Error**: Erro interno do servidor.

## Documentação OpenAPI
A documentação da API pode ser acessada em:

```bash
  http://localhost:8080/swagger-ui/index.html
```

