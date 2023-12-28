# Ordem de serviço para manutenção de equipamentos

## Passos iniciais para rodar o projeto
- Executar o `docker-compose.yml` utilizando o comando `docker-compose up`.
- Documentação das apis: http://localhost:8080/swagger-ui/index.html
- Foram criados dois usuários em memória para adicionar uma camada de segurança ao projeto. Esses dados precisam ser passados nas requisições através do Authorization Basic.
- ### Usuários
  - User: admin, Password: admin
  - User: user, Password: user

## Funcionalidades
- Criar uma Ordem de Serviço
  - Precisamos informar os dados do Cliente, Responsável e do Equipamento

- Iniciar e Finalizar uma Ordem de Serviço
  - É possível alterar o status de uma Ordem de Serviço indicando se ela está em processamento ou finalizada

- Adicionar um histórico de atividades relacionadas à uma Ordem de Serviço
  - Podemos informar as atividades relacionadas a Ordem de Serviço que está com o status de iniciada

- Buscar ordem de serviço pela matrícula do Responsável pela Ordem de Serviço
- Listar todas as Ordem de Serviços
- Buscar os detalhes de uma Ordem de Serviço

## Fluxos

### Cadastro de Ordem de Serviço
- Primeiramente é preciso fazer o Cadastro de uma Ordem de serviço. Isso é possível atrás do endpoint `POST /order` :
```shell
curl --location 'http://localhost:8080/order' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw '{
    "customer": {
        "name": "name",
        "phone": "99999-9999",
        "email": "test@email.com",
        "address": {
            "street": "street",
            "number": "S/N",
            "city": "city",
            "state": "state",
            "country": "country"
        }
    },
    "responsible": {
        "name": "Test",
        "registration": "MT4321"
    },
    "equipment": {
        "type": "type",
        "brand": "brand",
        "description": "description"
    }
}'
```
Resposta:
```json
{
    "id": "658d759656c555634c325daa",
    "orderId": 18,
    "customer": {
        "name": "name",
        "phone": "99999-9999",
        "email": "test@email.com",
        "address": {
            "street": "street",
            "number": "S/N",
            "city": "city",
            "state": "state",
            "country": "country"
        }
    },
    "responsible": {
        "name": "Test",
        "registration": "MT4321"
    },
    "equipment": {
        "type": "type",
        "brand": "brand",
        "description": "description"
    },
    "historyList": [],
    "status": "WAITING",
    "createdDate": "2023-12-28T09:18:14.936464302",
    "lastModifiedDate": "2023-12-28T09:18:14.936464302"
}
```

### Iniciando uma Ordem de Serviço
Após a criação da Order de Serviço o responsável pode informar o inicio do atendimento atravéz do endpoint `PATCH /start/{orderId}`:
```shell
curl --location --request PATCH 'http://localhost:8080/order/start/18' \
--header 'Authorization: Basic dXNlcjp1c2Vy' 
```

Isso fará com que a Ordem de Serviço mude seu status para `PROCESSING` indicando que ela está em andamento.

### Informando registro de acompanhamento
Após iniciar uma Ordem de Serviço o reponsável pode adicionar registros de acompanhamento atravéz do endpoint `PATCH /history`:
```shell
curl --location --request PATCH 'http://localhost:8080/order/history' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--data '{
    "orderId": 18,
    "description": "Iniciando manutenção"
}'
```
Resposta:
```json
{
    "id": "658d759656c555634c325daa",
    "orderId": 18,
    "customer": {
        "name": "name",
        "phone": "99999-9999",
        "email": "test@email.com",
        "address": {
            "street": "street",
            "number": "S/N",
            "city": "city",
            "state": "state",
            "country": "country"
        }
    },
    "responsible": {
        "name": "Test",
        "registration": "MT4321"
    },
    "equipment": {
        "type": "type",
        "brand": "brand",
        "description": "description"
    },
    "historyList": [
        {
            "date": "2023-12-28T09:28:16.708081283",
            "description": "Iniciando manutenção"
        }
    ],
    "status": "PROCESSING",
    "createdDate": "2023-12-28T09:18:14.936",
    "lastModifiedDate": "2023-12-28T09:28:16.708359013"
}
```

### Finalizando uma Ordem de Serviço
Ao terminar a manutenção o responsável pode finalizar a Ordem de Serviço atravéz do endpoint `PATCH /stop/{orderId}`:
```shell
curl --location --request PATCH 'http://localhost:8080/order/stop/18' \
--header 'Authorization: Basic dXNlcjp1c2Vy' 
```

### Buscando detalhes de uma Ordem de Serviço
A qualquer momento após a criação da Order de Serviço é possível buscar os detalhamentos atrávez do endpoint `GET /order/{orderId}`:
```shell
curl --location 'http://localhost:8080/order/18' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```
Resposta:
```json
{
    "id": "658d759656c555634c325daa",
    "orderId": 18,
    "customer": {
        "name": "name",
        "phone": "99999-9999",
        "email": "test@email.com",
        "address": {
            "street": "street",
            "number": "S/N",
            "city": "city",
            "state": "state",
            "country": "country"
        }
    },
    "responsible": {
        "name": "Test",
        "registration": "MT4321"
    },
    "equipment": {
        "type": "type",
        "brand": "brand",
        "description": "description"
    },
    "historyList": [
        {
            "date": "2023-12-28T09:28:16.708",
            "description": "Iniciando manutenção"
        }
    ],
    "status": "FINISHED",
    "createdDate": "2023-12-28T09:18:14.936",
    "lastModifiedDate": "2023-12-28T09:33:33.67"
}
```

### Buscando uma Ordem de Serviço pela matrícula do responsável
Podemos buscar todas as Ordens de Serviço de uma responsável através de sua matrícula pelo endpoint `GET /find-by-registration`:
```shell
curl --location 'http://localhost:8080/order/find-by-registration?registration=MT4321' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```
Resposta:
```json
[
    {
        "id": "658b9bc5b0f5ae4b37fcdd79",
        "orderId": 17,
        "customer": {
            "name": "name",
            "phone": "99999-9999",
            "email": "test@email.com",
            "address": {
                "street": "street",
                "number": "S/N",
                "city": "city",
                "state": "state",
                "country": "country"
            }
        },
        "responsible": {
            "name": "Test",
            "registration": "MT4321"
        },
        "equipment": {
            "type": "type",
            "brand": "brand",
            "description": "description"
        },
        "historyList": [
            {
                "date": "2023-12-26T23:37:43.346",
                "description": "Iniciando manutenção"
            },
            {
                "date": "2023-12-26T23:37:58.45",
                "description": "Solicitando peça xpto"
            },
            {
                "date": "2023-12-26T23:38:06.269",
                "description": "Trocando peça xpto"
            },
            {
                "date": "2023-12-26T23:38:25.205",
                "description": "Finalizando montagem"
            }
        ],
        "status": "FINISHED",
        "createdDate": "2023-12-26T23:36:37.042",
        "lastModifiedDate": "2023-12-26T23:38:45.294"
    },
    {
        "id": "658d759656c555634c325daa",
        "orderId": 18,
        "customer": {
            "name": "name",
            "phone": "99999-9999",
            "email": "test@email.com",
            "address": {
                "street": "street",
                "number": "S/N",
                "city": "city",
                "state": "state",
                "country": "country"
            }
        },
        "responsible": {
            "name": "Test",
            "registration": "MT4321"
        },
        "equipment": {
            "type": "type",
            "brand": "brand",
            "description": "description"
        },
        "historyList": [
            {
                "date": "2023-12-28T09:28:16.708",
                "description": "Iniciando manutenção"
            }
        ],
        "status": "FINISHED",
        "createdDate": "2023-12-28T09:18:14.936",
        "lastModifiedDate": "2023-12-28T09:33:33.67"
    }
]
```

### Buscando lista de Ordem de Serviço
É possível tbem buscar todas as Ordens de Serviço de maneira paginada atravéz do endpoint `GET /order`:
```shell
curl --location 'http://localhost:8080/order' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```
Resposta:
```json
{
    "content": [
        {
            "id": "658b9bc5b0f5ae4b37fcdd79",
            "orderId": 17,
            "customer": {
                "name": "name",
                "phone": "99999-9999",
                "email": "test@email.com",
                "address": {
                    "street": "street",
                    "number": "S/N",
                    "city": "city",
                    "state": "state",
                    "country": "country"
                }
            },
            "responsible": {
                "name": "Test",
                "registration": "MT4321"
            },
            "equipment": {
                "type": "type",
                "brand": "brand",
                "description": "description"
            },
            "historyList": [
                {
                    "date": "2023-12-26T23:37:43.346",
                    "description": "Iniciando manutenção"
                },
                {
                    "date": "2023-12-26T23:37:58.45",
                    "description": "Solicitando peça xpto"
                },
                {
                    "date": "2023-12-26T23:38:06.269",
                    "description": "Trocando peça xpto"
                },
                {
                    "date": "2023-12-26T23:38:25.205",
                    "description": "Finalizando montagem"
                }
            ],
            "status": "FINISHED",
            "createdDate": "2023-12-26T23:36:37.042",
            "lastModifiedDate": "2023-12-26T23:38:45.294"
        },
        {
            "id": "658d759656c555634c325daa",
            "orderId": 18,
            "customer": {
                "name": "name",
                "phone": "99999-9999",
                "email": "test@email.com",
                "address": {
                    "street": "street",
                    "number": "S/N",
                    "city": "city",
                    "state": "state",
                    "country": "country"
                }
            },
            "responsible": {
                "name": "Test",
                "registration": "MT4321"
            },
            "equipment": {
                "type": "type",
                "brand": "brand",
                "description": "description"
            },
            "historyList": [
                {
                    "date": "2023-12-28T09:28:16.708",
                    "description": "Iniciando manutenção"
                }
            ],
            "status": "FINISHED",
            "createdDate": "2023-12-28T09:18:14.936",
            "lastModifiedDate": "2023-12-28T09:33:33.67"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```

### Apagando uma Orderm de Serviço
A qualquer momento após a criação da Order de Serviço é possível apagar a Ordem de Serviço atravéz do endpoint `DELETE /order/{orderId}`:
```shell
curl --location --request DELETE 'http://localhost:8080/order/17' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```