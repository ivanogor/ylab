# Habit Tracker Application

## Homework One
[Ссылка на пул реквест](https://github.com/ivanogor/ylab/pull/1)

### Инструкции по запуску
1. Клонируйте репозиторий на свой локальный компьютер.
2. Откройте проект в вашей IDE.
3. Запустите класс `Main` для начала работы с приложением.

# Пример команд для проверки(Вводить отдельно через Enter)

Для проверки всего функционала приложения через консоль, можно использовать следующий список команд:

## Регистрация нового пользователя
1  
John Doe  
john.doe@example.com  
password123  

## Вход в систему
2  
john.doe@example.com  
password123  

## Обновление пользователя
3  
Jane Doe  
jane.doe@example.com  
newpassword123  

## Удаление пользователя
4  
john.doe@example.com  

## Сброс пароля
5  
john.doe@example.com  
newpassword456  

## Блокировка пользователя (требуется администратор)
6  
john.doe@example.com  

## Разблокировка пользователя (требуется администратор)
7  
john.doe@example.com  

## Создание привычки
8  
Exercise  
Daily exercise routine  
DAILY  

## Обновление привычки
9  
Exercise  
New Exercise  
Updated daily exercise routine  
DAILY  

## Удаление привычки
10  
New Exercise  

## Получение всех привычек
11  

## Получение привычек по дате создания
12  
2023-10-01  

## Получение привычек по частоте
13  
DAILY  

## Отметка привычки как выполненной
14  
Exercise  
2023-10-01  

## Подсчет выполнений привычки за период
15  
Exercise  
DAY  

## Получение текущей серии выполнений
16  
Exercise  

## Получение процента выполнения привычки
17  
Exercise  
2023-10-01  
2023-10-31  

## Генерация отчета о прогрессе пользователя
18  
2023-10-01  
2023-10-31  

## Получение всех пользователей (требуется администратор)
19   

## Выход
0


# Описание проекта

Этот проект представляет собой простое приложение для отслеживания привычек, разработанное в рамках первого домашнего задания. Приложение позволяет пользователям регистрироваться, управлять своими привычками, отслеживать их выполнение и просматривать статистику. В проекте использованы паттерны проектирования Builder, Command и DTO для улучшения структуры и расширяемости кода.

# Основные функции

1. **Регистрация и авторизация пользователей**
   - Возможность регистрации новых пользователей с уникальным email и паролем.
   - Вход в систему с проверкой email и пароля.

2. **Управление пользователями**
   - Возможность редактирования профиля пользователя (имя, email, пароль).
   - Возможность удаления аккаунта.
   - Реализация сброса пароля через email (опционально).

3. **Управление привычками (CRUD)**
   - Создание привычки: Пользователь может создать новую привычку с указанием названия, описания и частоты (ежедневно, еженедельно).
   - Редактирование привычки: Возможность изменения информации о привычке.
   - Удаление привычки: Удаление привычки и всей связанной статистики выполнения.
   - Просмотр привычек: Возвращение списка всех привычек пользователя с возможностью фильтрации по дате создания или статусу.

4. **Отслеживание выполнения привычек**
   - Пользователь может ежедневно отмечать выполнение привычки.
   - Хранение истории выполнения для каждой привычки.
   - Генерация статистики выполнения привычки за указанный период (день, неделя, месяц).

5. **Статистика и аналитика**
   - Подсчет текущих серий выполнения привычек (streak).
   - Процент успешного выполнения привычек за определенный период.
   - Формирование отчета для пользователя по прогрессу выполнения.

6. **Уведомления (опционально)**
   - API для отправки напоминаний пользователю о необходимости выполнения привычки.
   - Возможность интеграции с внешними сервисами для отправки email или push-уведомлений.

7. **Администрирование**
   - Администраторы могут получать доступ к списку пользователей и привычек (опционально).
   - Возможность блокировки или удаления пользователей.

# Использованные паттерны проектирования

## Паттерн Builder
- Используется для создания сложных объектов с множеством параметров. В проекте паттерн Builder применяется для создания объектов классов `User` и `Habit`.

## Паттерн Command
- Используется для инкапсуляции запросов как объектов, что упрощает управление ими и позволяет параметризовать клиентские объекты с операциями, ставить запросы в очередь или протоколировать их, а также поддерживать отмену операций. В проекте паттерн Command применяется для реализации всех операций с пользователями и привычками.

## Паттерн DTO (Data Transfer Object)
- Используется для передачи данных между слоями приложения. В проекте DTO объекты используются для передачи данных между сервисами и командами.

# Автор

- **Имя**: Огорь Иван

# Инструкции по запуску

1. Клонируйте репозиторий на свой локальный компьютер.
2. Откройте проект в вашей IDE.
3. Запустите класс `Main` для начала работы с приложением.

# Зависимости

- Java 22
- Lombok (для генерации геттеров, сеттеров и других методов)
