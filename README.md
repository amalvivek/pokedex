## Pokedex API

---
### Requirements
- Docker
- JAVA 11 (if building locally)
___

- `./gradlew run` to run locally
- `./gradlew test` to run unit tests
  
---
### Building Docker Image
- `./gradlew assemble` to build jar files
- `docker build . -t pokedex` to build docker image
- `docker run --rm -p 8080:8080 pokedex` to run, will deploy to `http://localhost:8080/`

---

### API

`GET /pokemon/{name}`

`GET /pokemon/translated/{name}`

