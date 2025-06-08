# test-jules

---

## Running with Docker Compose

This project includes a `docker-compose.yml` file to easily run the application and a MongoDB database locally.

### Prerequisites

*   [Docker](https://docs.docker.com/get-docker/)
*   [Docker Compose](https://docs.docker.com/compose/install/) (usually included with Docker Desktop)

### Steps

1.  **Build and Run:**
    Open a terminal in the project root directory and run:
    ```bash
    docker-compose up -d --build
    ```
    *   `--build`: Forces Docker Compose to rebuild the application image if there are changes in the `Dockerfile` or application code.
    *   `-d`: Runs the containers in detached mode (in the background).

2.  **Accessing the API:**
    Once the containers are up and running, the Product API will be accessible at `http://localhost:8080`.
    For example, to get all products:
    ```
    http://localhost:8080/products
    ```

3.  **Stopping the application:**
    To stop and remove the containers, run:
    ```bash
    docker-compose down
    ```
    If you want to remove the MongoDB data volume as well (all data will be lost), run:
    ```bash
    docker-compose down -v
    ```
