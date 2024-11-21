# comp-financeira - Componente Financeiras Tabelas de Juros

# Projeto Componente financeira

- SrpingBoot 3.1.1
- Java 17
- Banco de dados PostgreSQL Com Hibernate e SessionFacory
- Maven (Recomendado versão (apache-maven-3.8.7)
- JUnit 5
- Liquibase
- Mockito
- Swagger  `` ( http://localhost:10070/api/swagger-ui/index.html ) ``

# Para acesar a aplicação

```
# localhost:10070/api
```

## Swagger


```
http://localhost:10070/api/swagger-ui/index.html
```


## Maven build debug in Eclipse
Easiest way I find is to:

1 - Right click project

2 - Debug as -> Maven build ...

3 - In the goals field put -Dmaven.surefire.debug test

4 - In the parameters put a new parameter called forkCount with a value of 0 (previously was forkMode=never but it is deprecated and doesn't work anymore)





## Mavem liquibase line command

- rollback com mavem

```
# liquibase:rollback -Dliquibase.rollbackTag=tag-cliente -Plocal -X
```


Em produção

```
# java -jar app.jar executar --liquibase.rollback-tag=tag-twa_cliente_contato_view --liquibase.contexts=local
```


#### Para executar o comando liquibase local, alimentar as variaveis conforme, valor no application.yml

* DB_HOST: localhost
* DB_PORT: 5433
* DB_NAME: name_banco
* DB_USERNAME: user_database
* DB_PASSWORD: password_user_database


```
# liquibase:update -Plocal
```

- update

update last tag

```
# gradle liquibaseLocal
```

# Docker

* Docker used for test with sonar
* sonar default 
* user=admin password =admin

```
docker build -t comp-cliente-service .
```

### Windows

```
# open powershell
# wsl -d docker-desktop
# sysctl -w vm.max_map_count=262144
# exit
```
## sonarqube

Run a docker sonarqube

```
# docker-compose up
```

###  Analyzing

Run qulity code

```
mvn clean verify -P sonar
```

## Git (github.com)

Para gerar uma chave publica para github via shh

https://github.com/settings/profile

(Na estação local de trabalho)

Digito o comando abaixo:

ssh-keygen -t ed25519 -C "seu_email@test.com"

Depois abra o arquivo "id_ed25519.pub"
Copie a chave e adiocione no link (Abaixo) "New Shh Key"

-> https://github.com/settings/keys
