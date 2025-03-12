

<h2>2 вариант</h2>
<h3>Запуск проекта</h3>
<h4> Запускается бек и postges </h4>
```bash
docker compose up
```

<h4> Зависимости </h4>
<table>
<tr><th>Библиотека</th><th>Версия</th> <th></th></tr>
<tr><td>jersey</td><td>3.1.0</td></tr>
<tr><td>hk2</td><td>3.1.0</td> <th>CDI</th></tr>
<tr><td>hibernate</td><td>6.3.1</td></tr>
<tr><td>postgresql</td><td></td><th>БД</th></tr>
<tr><td>hikaricp</td><td>5.2.6</td><th>Пул к бд</th></tr>
<th>Тесты</th>
<tr><td>mockito</td><td>4.0.0</td><th></th></tr>
<tr><td>junit</td><td>5.7.0</td><th></th></tr>
<tr><td>h2</td><td>1.4</td><th>Интеграционные тесты</th></tr>
</table>

Для локальной разработке были добавлены следующие зависимости
```
export DATABASE_URL=jdbc:postgresql://localhost:5432/library
export DATABASE_USER=user
export DATABASE_PASSWORD=password
export LIBRARY_PORT
```

<h3>Для более удобной работы с апи можно импортировать json в postman</h3>
`postmanLibrary.json`

<h2>Описание сервиса</h2>

<div> Работник библиотеки управляет пользователями, их арендой книг, а также получает аналитику по аренде конкретного пользователя</div>
<div> Есть определенное количество книг в классе Book это count, при работе сервиса нельзя выйти в минус по количеству</div>
<div> Время выдачи и сдачи книги устанавливается пользователем вручную. Однако если время не заполнено в параметрах, то по умолчанию сохраняется текущее время для выдачи или сдачи книги LocalDateTime.now().</div>


<h4>4 контроллера `org.cwt.task.config`</h4>
<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `UserResource` - Ведение базы пользователей</h3>
    <ul>
        <li><strong>GET</strong> /api/user: Получить список всех пользователей.</li>
        <li><strong>POST</strong> /api/user: Создать нового пользователя.</li>
        <li><strong>GET</strong> /api/user/{id}: Получить информацию о пользователе по ID.</li>
        <li><strong>DELETE</strong> /api/user/{id}: Удалить пользователя по ID.</li>
    </ul>
</div>

<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `RentResource` - Управление арендой книг</h3>
    <ul>
        <li><strong>GET</strong> /api/rent: Получить список всех выдач/сдач.</li>
        <li><strong>POST</strong> /api/rent: Взять книгу.</li>
        <li><strong>GET</strong> /api/rent/user/{userId}: Получить все аренды для пользователя по ID.</li>
        <li><strong>PUT</strong> /api/rent/{rentId}: Вернуть книгу.</li>
    </ul>
</div>

<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `AnalyticsResource` - Аналитика использования книг</h3>
    <ul>
        <li><strong>GET</strong> /api/analytic/user/{userId}: Получить аналитику по пользователю за указанный период.</li>
    </ul>
</div>

<h4> Модели </h4>
<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `Book` - Модель книги</h3>
    <ul>
        <li><strong>id</strong>: Уникальный идентификатор книги (тип: Long).</li>
        <li><strong>name</strong>: Название книги (тип: String).</li>
        <li><strong>author</strong>: Автор книги (тип: String).</li>
        <li><strong>publicationDate</strong>: Дата публикации книги (тип: LocalDate).</li>
        <li><strong>count</strong>: Количество книг на складе (тип: Integer), не может быть отрицательным (при попытке вылет ошибки).</li>
        <li><strong>bookTheme</strong>: Тема книги (тип: BookTheme), может быть одним из значений: CLASSIC, FANTASTIC, EPIC, DRAMA, DETECTIVE.</li>
    </ul>

    <h4>Перечень возможных тем книг:</h4>
    <ul>
        <li>CLASSIC</li>
        <li>FANTASTIC</li>
        <li>EPIC</li>
        <li>DRAMA</li>
        <li>DETECTIVE</li>
    </ul>
</div>

<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `BookRent` - Модель аренды книги</h3>
    <ul>
        <li><strong>id</strong>: Уникальный идентификатор аренды (тип: UUID, автоматически генерируется).</li>
        <li><strong>rentDate</strong>: Дата и время начала аренды (тип: LocalDateTime).</li>
        <li><strong>returnDate</strong>: Дата и время возврата книги (тип: LocalDateTime).</li>
        <li><strong>user</strong>: Пользователь, арендующий книгу (связь с моделью User, тип: User).</li>
        <li><strong>book</strong>: Книга, которая арендуется (связь с моделью Book, тип: Book).</li>
        <li><strong>rentStatus</strong>: Статус аренды (тип: RentStatus), может быть значением OPENED (открыта) или CLOSED (закрыта).</li>
    </ul>

    <h4>Перечень возможных статусов аренды:</h4>
    <ul>
        <li>OPENED</li>
        <li>CLOSED</li>
    </ul>
</div>

<div style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h3> `User` - Модель пользователя</h3>
    <ul>
        <li><strong>id</strong>: Уникальный идентификатор пользователя (тип: UUID, автоматически генерируется).</li>
        <li><strong>firstName</strong>: Имя пользователя (тип: String).</li>
        <li><strong>lastName</strong>: Фамилия пользователя (тип: String).</li>
        <li><strong>age</strong>: Возраст пользователя (тип: Byte).</li>
        <li><strong>email</strong>: Электронная почта пользователя (тип: String), уникальная в системе.</li>
        <li><strong>bookRents</strong>: Список арендуемых книг пользователя (связь с моделью BookRent, тип: List<BookRent>).</li>
    </ul>
</div>


<h2>Из особенного</h2>
<div>Для аналитики пользователя /api/analytic/user/{userId} Использую уровень изоляции транзакции Repeatable Read, так как при параллельных транзакциях при уровне Read Commited количество книг может выйти за 0, что приведет к несоответсвию в библиотеке</div>
<div>Информация собирает относительно времени. То есть, если аренда на текущий момент имеет статус CLOSED, но на промежуток времени она была всё еще OPENED, тогда в аналитике у пользователя она будет всё ещё OPENED</div>