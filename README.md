# Gerenciamento de Eventos - Spring Boot
Este projeto consiste em um sistema simples de gerenciamento de eventos, desenvolvido em Java com o framework Spring Boot. Ele permite a criação, leitura, atualização e exclusão de eventos, além de funcionalidades adicionais, como cálculo de duração dos eventos e regras de negócio envolvendo datas.

# Funcionalidades
CRUD de Eventos: Criar, ler, atualizar e excluir eventos.
Cálculo de duração de eventos: Endpoint para calcular e retornar a duração de um evento em horas e minutos.
Paginação e Ordenação: Paginação e ordenação para a listagem de eventos.
Filtro por Período: Endpoint para buscar eventos que ocorrem dentro de um intervalo de tempo específico.
Validações de Datas: Validação que garante que a data e hora de término do evento seja posterior à data e hora de início.
Documentação de APIs: APIs documentadas para facilitar o uso e integração.

# Tecnologias Utilizadas
Java
Spring Boot
Spring Web
Spring Data JPA
Banco de Dados H2 (ou outro configurável)
Swagger
JUnit
Docker


# Endpoints
POST /events: Criar um novo evento.
GET /events: Listar todos os eventos (com paginação e ordenação).
GET /events/{id}: Obter detalhes de um evento específico.
PUT /events/{id}: Atualizar um evento existente.
DELETE /events/{id}: Excluir um evento.
GET /events/duration/{id}: Retorna a duração de um evento em horas e minutos.
GET /events/within-period?start={start}&end={end}: Retorna eventos dentro de um período de tempo específico.
