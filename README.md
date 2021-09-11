# personapi

## Enpoints

Uri|Método|Descrição|Request Body|Response Body|Status
---|------|---------|------------|-------------|------
/api/v1/people|GET|obter todos os registros|n/a|coleção de Person|200/404
/api/v1/people/{id}|GET|buscar um registro pelo id|n/a|Person|200/404
/api/v1/people|POST|criar um novo registro|Person sem o id|Person|201/400
/api/v1/people/{id}|PUT|modificar os dados de um registro|Person|n/a|200/400/404
/api/v1/beers/{id}|DELETE|remover um registro do banco de dados|n/a|n/a|204/404

- **Person**

```Json
    {
        "firstName": "Friedrich",
        "lastName": "Hayek",
        "cpf": "35562354470",
        "birthDate": "1899-03-08",
        "phones": [
          {
            "type": "MOBILE",
            "number": "(99)99999-9999"
          },
          {
            "type": "HOME",
            "number": "(88)88888-8888"
          }
        ]
    }
```

- **Telefones**

```Json
    {
        "type": "HOME",
        "type": "MOBILE",
        "type": "COMMERCIAL"
    }
```
