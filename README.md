# Парсер для маркетплейса Wildberries

## Задача
Написать парсер для маркетплейса Wildberries, который собирает такие данные как: Название товара, название бренда, количество отзывов, средняя оценка и ссылка на карточку товара

## Стэк
Spring(Boot, Data, Validator), JUnit, Selenium, Kafka, Docker, PostgreSQL, Kafdrop, Adminer, Lombok, Javadoc, logging

## Установка и запуск
### Порты
Для начала необъодимо проверить не используются ли в данных момент порты: 2181, 9092, 9000, 5432, 8083, 8082, 8081
### Требования
Для запуска приложения необходима установка следующих зависимостей:
- Docker 

### Установка
1. Склонируйте репозиторий с проектом:
    ```bash
    git clone https://github.com/0-Bober-0/ParserWB.git
    ```
2. Перейдите в директорию проекта:
    ```bash
    cd ParserWB/ParserWB
    ```
3. Запустите проект с помощью Docker:
   ```bash
   docker-compose up
    ```
## Взаимодействие
### С сайтом
Для взаимодействия с главной страницей, нужно перейти по адресу http://localhost:8083/products

### С базой данных
Для удобства был добавлен контейнер с Adminer для взаимодействия с базой данных: http://localhost:8081
#### Данные для входа:
- **Движок**: PostgreSQL
- **Сервер**: db
- **Имя пользователя**: postgres
- **Пароль**: postgres
- **База данных**: products

### С сообщениями Kafka
Для просмотра отправленных сообщений http://localhost:9000

## Javadoc
### Генерация документации
Для генерации документации необходимо перейти в директорию проект Parser или Server и написать в терминал
   ```bash
   mvn javadoc:javadoc  
   ```
### Просмотр документации
Документация будет сгенерирована в файлы по путям: 
- **"ParserWB\ParserWB\Parser\target\reports\apidocs\index.html"**
- **"ParserWB\ParserWB\Server\target\reports\apidocs\index.html"**

## Скриншоты работы
### Стартовая страница
![image](https://github.com/user-attachments/assets/3da17209-9073-4f45-890d-fea399cf378f)

### Окончательная страница
![image](https://github.com/user-attachments/assets/addfa5bd-8cec-4593-a022-b48cadccfdbc)

### Результат парсинга в Adminer
![image](https://github.com/user-attachments/assets/c742972e-1d08-487b-a047-2cbb2c1cda69)

### Проверка на пустую строку
![image](https://github.com/user-attachments/assets/4b791e41-3b67-49db-ae29-faf3ba52d4f0)

### Проверка на неправильно введённый URL
![image](https://github.com/user-attachments/assets/815c9af1-4f59-4702-bf46-fc1ea89aaf3a)

### Логирование
![image](https://github.com/user-attachments/assets/490ae4f3-a383-446b-8cc7-727eefbf1f7b)

### Страница Kafdrop
![image](https://github.com/user-attachments/assets/9f7855c4-038e-4c2e-b660-8621e86cf1ab)

### Сообщения в топике
![image](https://github.com/user-attachments/assets/5d3d2b95-ab1f-4971-91bc-a11e002db15c)









