# Galo indio

Criei esse mini-projeto em Java para o [Rinha de Backend](https://github.com/zanfranceschi/rinha-de-backend-2024-q1) 
que é um projeto que visa compartilhar conhecimento em formato de competição.

O nome desse projeto pode parecer estranho, mas dizem os antigos que galo índio é bom de briga 🐓

Esse projeto foi escrito utilizando as seguintes tecnologias disponibilizando uma API REST:

- Java 21
- Spring Boot 3.2.3
- Spring Data
- Spring Web
- PostgreSQL

## Building

Para fazer o build do projeto, basta executar o script abaixo:
```
./build-docker-image.sh
```

Esse script vai fazer o build do projeto que vai gerar um `jar` da aplicação.
Em seguida o script cria uma imagem Docker que permite executar a aplicação em um container Docker.
