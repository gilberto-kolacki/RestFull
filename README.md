# API RESTful Golden Raspberry Awards
### Build
### Abrir o terminal.
Para gerar o .jar da aplicação, execute:

    ./mvnw clean package

Para executar a aplicação, execute:

    java -jar target/restfull*.jar

Para acessar a API:

    http://localhost:8081

Para acessar o Banco de Dados:

    http://localhost:8081/h2-console

Configurações do banco H2:

    url: jdbc:h2:mem:restdb
    username: sa
    password:
    driver: org.h2.Driver

### Rodar Testes

Para iniciar os testes, execute:

    ./mvnw clean test



# Especificação do Teste
Desenvolva uma API RESTful para possibilitar a leitura da lista de 
indicados e vencedores da categoria Pior Filme do 
Golden Raspberry Awards.

### Requisito do sistema

1 Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao iniciar a
aplicação.

### Requisitos da API:
Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que
obteve dois prêmios mais rápido, seguindo a especificação de formato definida;
```json    
    Ex:
        {
            "min": [
                {
                    "producer": "Producer 1",
                    "interval": 1,
                    "previousWin": 2008,
                    "followingWin": 2009
                },
                {
                    "producer": "Producer 2",
                    "interval": 1,
                    "previousWin": 2018,
                    "followingWin": 2019
                }
            ],
            "max": [
                {
                    "producer": "Producer 1",
                    "interval": 99,
                    "previousWin": 1900,
                    "followingWin": 1999
                },
                {
                    "producer": "Producer 2",
                    "interval": 99,
                    "previousWin": 2000,
                    "followingWin": 2099
                }
            ]
        }
```
### Requisitos não funcionais do sistema:

1. O web service RESTful deve ser implementado com base no nível 2 de maturidade
de Richardson;
2. Devem ser implementados somente testes de integração. Eles devem garantir que
  os dados obtidos estão de acordo com os dados fornecidos na proposta;
3. O banco de dados deve estar em memória utilizando um SGBD embarcado (por
   exemplo, H2). Nenhuma instalação externa deve ser necessária;
4. A aplicação deve conter um readme com instruções para rodar o projeto e os
   testes de integração.

    
    O código-fonte deve ser disponibilizado em um repositório git (Github, Gitlab, Bitbucket, etc).