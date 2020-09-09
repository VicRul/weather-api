# weather-api
Тестовое задание - работа с API сайта погоды.
* SpringBoot
* Spring Data JPA
* PostgreSQL
* Retrofit
* Lombok
## Установка и запуск
Предварительно необходимо создать БД:
```SQL
CREATE DATABASE weatherdb
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'ru_RU.UTF-8'
    LC_CTYPE = 'ru_RU.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```
Приложение, после запуска, доступно по адресу: `http://localhost:9090/`.
## Примеры запросов
Поддерживаются следующие типы запросов:
* Средняя, максимальная и минимальная температуры за период
```http
http://localhost:9090/temp?dateStart=2020-09-06&dateEnd=2020-09-09
```
* Максимальный уровень радиации за период: 
```http
http://localhost:9090/rad?dateStart=2020-09-04&dateEnd=2020-09-09
```
