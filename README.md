# RBAC API
Api restful que representa a relação de contas de usuários (Users), perfis (Profiles) e permissões (Roles).

Através da API devemos relaizar as operações de CRUD dos recursos mencionados, e tornar possível relacioná-los.

**Ferramentas**

> [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
	
> [Eclipse](https://www.eclipse.org/downloads/) - Ambiente de Desenvolvimento - IDE

> [NetBeans](https://netbeans.apache.org/download/index.html) - Ambiente de Desenvolvimento - IDE

> [GIT](https://git-scm.com/downloads) - Ferramenta de SCM

> [Maven](https://maven.apache.org/download.cgi) - Ciclo de Vida

**Repositório**

> [Github](https://github.com/FilipeSoares/rbac-api)

**Servidores de Aplicação**

> [REDHAT JBOSS EAP](https://developers.redhat.com/products/eap/download/)

> [Wildfly](http://wildfly.org/downloads/)

**Clone do projeto (Git)**

`git clone https://github.com/FilipeSoares/rbac-api ./[path]`

## Ciclo de Vida

### Build
`mvn package`

### Testes Unitários

`mvn -U clean test`

### Testes de Integração

`mvn -U clean verify`

## Documentação

**Swagger**

> http://[localhost]:[8080]/rbac-api/v1/api-docs/#/

> http://[localhost]:[8080]/rbac-api/v1/swagger.json

## Autor

> [Filipe Oliveira](https://github.com/FilipeSoares)
