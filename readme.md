# Ecommerce Backend Project

This project is an ecommerce backend application developed using Java EE technologies. It provides RESTful APIs for managing products, categories, clients, and sales.

## Requirements

- Java 8
- WildFly 18.0.1
- PostgreSQL

## Project Structure

- `src/main/java/py/com/progweb/prueba/ejb`: Contains the EJB components for business logic.
- `src/main/java/py/com/progweb/prueba/model`: Contains the JPA entities.
- `src/main/java/py/com/progweb/prueba/rest`: Contains the RESTful web services.
- `src/main/resources/META-INF`: Contains the persistence configuration.
- `src/main/webapp/WEB-INF`: Contains the web application configuration.

## Dependencies

- Hibernate 5.6.15.Final
- CDI API 1.2
- EJB API 3.2
- JAX-RS API 2.1.1
- Jackson Databind 2.15.3
- Dom4j 1.6.1
- Jakarta EE API 8.0.0
- PostgreSQL Driver 42.2.18

## Configuration

### Persistence

The persistence configuration is located in `src/main/resources/META-INF/persistence.xml`. It uses PostgreSQL as the database.

### WildFly

The project is configured to be deployed on WildFly 18.0.1. The datasource configuration for PostgreSQL should be added to the WildFly configuration.

### Build

The project uses Maven for build management. The `pom.xml` file contains the necessary dependencies and plugins.

## Deployment

1. Build the project using Maven:
    ```sh
    mvn clean install
    ```

2. Deploy the generated WAR file (`target/prueba.war`) to WildFly 18.0.1.

3. Configure the PostgreSQL datasource in WildFly.

## Usage

The application provides the following RESTful APIs:

- `/api/producto`: Manage products.
- `/api/categoria`: Manage categories.
- `/api/cliente`: Manage clients.
- `/api/venta`: Manage sales.
- `/api/consulta-venta`: Query sales.
- `/api/consulta-detalle-venta`: Query sale details.

## License

This project is licensed under the MIT License.