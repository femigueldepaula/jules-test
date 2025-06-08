# test-jules

---

## Executando com Docker Compose

Este projeto inclui um arquivo `docker-compose.yml` para executar facilmente a aplicação e um banco de dados MongoDB localmente.

### Pré-requisitos

*   [Docker](https://docs.docker.com/get-docker/)
*   [Docker Compose](https://docs.docker.com/compose/install/) (geralmente incluído no Docker Desktop)

### Passos

1.  **Construir e Executar (Build and Run):**
    Abra um terminal no diretório raiz do projeto e execute:
    ```bash
    docker-compose up -d --build
    ```
    *   `--build`: Força o Docker Compose a reconstruir a imagem da aplicação se houver alterações no `Dockerfile` ou no código da aplicação.
    *   `-d`: Executa os contêineres em modo detached (em segundo plano).

2.  **Acessando a API:**
    Assim que os contêineres estiverem em execução, a API de Produtos estará acessível em `http://localhost:8080`.
    Por exemplo, para obter todos os produtos:
    ```
    http://localhost:8080/products
    ```

3.  **Parando a aplicação:**
    Para parar e remover os contêineres, execute:
    ```bash
    docker-compose down
    ```
    Se você deseja remover também o volume de dados do MongoDB (todos os dados serão perdidos), execute:
    ```bash
    docker-compose down -v
    ```
