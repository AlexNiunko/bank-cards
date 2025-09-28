🏦 Система Управления Банковскими Картами
Система для управления банковскими картами с ролевой моделью доступа, 
обеспечивающая безопасное создание, блокировку, переводы между картами и просмотр баланса.

🚀 Технологии
- Java 17+ - основной язык разработки
- Spring Boot 3 - фреймворк
- Spring Security + JWT - аутентификация и авторизация
- Spring Data JPA - работа с базой данных
- PostgreSQL - база данных
- Liquibase - миграции базы данных
- Docker & Docker Compose - контейнеризация
- Swagger - документация API

Maven - сборка проекта

📋 Функциональность
  👨‍💼 Администратор (ADMIN) 
  ✅ Создание, блокировка, активация, удаление карт  
  ✅ Добавление нового пользователя, удаление, блокирование конкретного пользователя, просмотр всех пользователей
  ✅ Просмотр всех карт в системе
  ✅ Фильтрация и пагинация карт

👤 Пользователь (USER)
  ✅ Просмотр своих карт 
  ✅ Запрос на блокировку карты
  ✅ Переводы между своими картами
  ✅ Просмотр баланса

🔐 Безопасность
  ✅ Шифрование данных карт в БД(номер карты), пользователя(пароль пользователя)
  ✅ Маскирование номеров карт (**** **** **** 1234) , маскирование телефона пользователя и его email 
  ✅ JWT токены для аутентификации
  ✅ Ролевая модель доступа

🛠 Локальное развертывание:
Требования: Windows 10/11 с Docker Desktop (WSL 2 backend), 2 CPU, 8 GB RAM, GNU Make (choco install make) или PowerShell 5+.

🚀 Быстрый запуск
1. Клонирование проекта
bash
git clone <url-репозитория>
cd bank_rest
2. Запуск через Docker Compose (из директории проекта)
bash
docker-compose up -d
3. Проверка статуса
bash
docker-compose ps
4. Просмотр логов (опционально)
bash
docker-compose logs -f bank-rest
🌐 Доступ к приложению
После успешного запуска приложение будет доступно:

Основное приложение: http://localhost:8080
Swagger UI документация: http://localhost:8080/swagger-ui/index.html#/
База данных PostgreSQL: localhost:5432

📚 API Документация
Полная документация API доступна через Swagger UI:

Основные эндпоинты:
🔐 Аутентификация
   POST /bank-rest/auth - аутентификация и получение JWT токена(Доступен всем)

💳 Управление картами (требуют аутентификации, доступен только ADMIN) 
   POST /bank-rest/card/create - создание карты (ADMIN)
   POST /bank-rest/card/update - обновление статуса карты (ADMIN)
   DELETE /bank-rest/card/delete - удаление карты (ADMIN)
   GET /bank-rest/card/get-all-cards - получение всех карт (ADMIN)
   POST /bank-rest/card/get-all-cards-by-filter - фильтрация карт (ADMIN)
   POST /bank-rest/card/get-all-cards-using-page - пагинация карт (ADMIN)
   
👤 Управление пользователями (требуют аутентификации, доступен только ADMIN) 
   GET /bank-rest/user/add-new-user - добавить нового пользователя (ADMIN)
   POST /bank-rest/user/get-all-users - получить всех пользователей (ADMIN)
   POST /bank-rest/user/delete-user - удалить пользователя (ADMIN)
   POST /bank-rest/user/update-user - оновить статус пользователя (ADMIN)

👤 Пользовательские операции (требуют аутентификации, доступен только USER) 
   GET /bank-rest/user-operation/get-user-cards - получение карт пользователя (USER)
   POST /bank-rest/user-operation/transfer - перевод между картами (USER)
   POST /bank-rest/user-operation/balance - просмотр баланса (USER)
   POST /bank-rest/user-operation/request-block-card - запрос блокировки карты (USER)

🔧 Конфигурация
Переменные окружения:
JWT_LIFETIME=30m - время жизни JWT токена
ENCODER_SECRET - секретный ключ для шифрования
ENCODER_ALGORITM=AES - алгоритм шифрования
SPRING_DATASOURCE_URL - URL базы данных
SPRING_DATASOURCE_USERNAME - пользователь БД
SPRING_DATASOURCE_PASSWORD - пароль БД

🗄 База данных
Тип: PostgreSQL 16.2
Порт: 5432
Пользователь: postgres
Пароль: postgres
База данных: postgres

Миграции базы данных выполняются автоматически через Liquibase при запуске приложения.БД будет заполнена тестовыми данными. 
 Для работы с API необходимо сначала пройти аутентификацию через /bank-rest/auth и использовать полученный JWT токен в заголовке Authorization: Bearer <token>.
Для тестирования в Swagger , также необходимо вначале пройти аутентификацию, а затем используя полученный токен (скопировать из ответа и вставить  в поле value  используя 
button <img width="218" height="61" alt="image" src="https://github.com/user-attachments/assets/7028fe3b-7f8e-4c37-95af-e7cad2f163d8" />). Для основных операций
тспользуются Id карт и пользователей.
  Для получения доступа к ADMIN ресурсам :
       {
    "login": "alex@gmail.com",
    "password": "123"
       }
       
  Для получения доступа к USER ресурсам :
       {
    "login": "ivan@mail.ru",
    "password": "456"
       }     


