# Password Validator API
Aplicação RESTful desenvolvida utilizando SpringBoot.

O objetivo do serviço é disponibilizar um endpoint para validação de senha de acordo com os requisitos estabelecidos.

## Requisitos de Senha

* Nove ou mais caracteres
* Ao menos 1 dígito
* Ao menos 1 letra minúscula
* Ao menos 1 letra maiúscula
* Ao menos 1 caractere especial
  * Considere como especial os seguintes caracteres: !@#$%^&*()-+
* Não possuir caracteres repetidos dentro do conjunto

## Detalhes Solução

#### Solução
Primeira decisão foi o uso de HTTP POST ao invés de HTTP GET (mesmo que não ocorra mudança de estado do lado servidor), devido a informação sensível que será enviada, e de acordo com o [OWASP - Open Web Application Security Project](https://owasp.org) não deve ser utilizado GET nestes cenários. [Mais informações](https://cheatsheetseries.owasp.org/cheatsheets/REST_Security_Cheat_Sheet.html#sensitive-information-in-http-requests)

Segundo ponto foi forma que a senha seria validada, a escolha foi o uso de REGEX para o cenário, pois diante dos pré-requisitos para validação se encaixa bem a técnologia.

A regra de negócio foi dividida em 3 momentos/validações:
* Validação dos requisitos principais
```regex
^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()-+]).{9,}$
````

* Validação de caracteres repetidos não sequenciais
```regex
(.)(?=.+\1)
```

* Validação de caracteres repetidos sequenciais
```regex
(.)\1
```

As três validações ocorrem uma após a outra, caso alguma ocorra com falha o resultado é retornado.

#### Testes Unitários
Para testes unitários foi utilizado JUnit com Mockito, enviando os parametros descrito no desáfio.

Exemplo de valores para senha inválida:
```java
static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "",
                "aa",
                "ab",
                "AAAbbbCc",
                "AbTp9!foo",
                "AbTp9!foA",
                "AbTp9 fok"
        );
    }
```

#### Cuidados
Foi utilizado o recurso @RequestBody do SpringFramework Web em conjunto com o @JsonIgnoreProperties do Jackon para mapeameto dos parametros de entrada e segurança da aplicação evitando de receber parametros indevidos. OU seja, qualquer parametro diferente do solicitado será ignorado.





## Dependências
Está sendo utilizado dependências básicas para a executação do projeto e seus testes. Verifique o arquivo [POM.XML](pom.xml) para mais detalhes.

## Executando o projeto
Você vai precisar:

* Java JDK 11 ou superior
* Maven 3.8.6 ou superior
* Git

Faça o clone do projeto
```
$ git clone https://github.com/zitbr/password-validator-api.git
```

Dentro da pasta do projeto, realize o build/install

```
$ mvn clean install
```

Após o build com sucesso, execute o projeto.

```
$ mvn spring-boot:run
```

A aplicação ficará disponivel na porta 8000, pode realizar os testes

## Testando

```http
POST /api/password/validate
```

```
$ curl -v -H "Content-type: application/json" -d '{"password":"suasenha"}' http://localhost:8000/api/password/validate
```

#### Body Request
```json
{
  "password": "suasenha"
}
```
#### Headers Request
| Header | Value |
|:--- | :--- |
|`Content-Type` | `application/json` |

#### Retorno

```
{
  "data": bool,               # Indicador se a senha está válida ou não
  "message": string,          # Mensagem de retorno referente a validação
  "errors": array of string   # Lista de erros referente a validação
}
```
#### Retorno 200 OK

```json
{
  "data": true,
  "message": "Password OK",
  "errors": []
}
```

#### Retorno 400 BadRequest

```json
{
  "data": false,
  "message": "Bad Password",
  "errors": [
    "Password failed validation."
  ]
}

