# Система управления библиотечным фондом

Лабораторная работа №15, вариант 3.

## Назначение

Консольное приложение для учёта печатных книг и электронных изданий.
Программа умеет загружать материалы из файла, искать их, выдавать читателю,
возвращать материалы, рассчитывать штраф за просрочку печатной книги,
добавлять новые материалы и сохранять изменения.

## Возможности

- печатные книги и электронные издания;
- добавление материалов;
- поиск по названию;
- поиск только доступных материалов;
- выдача и возврат материалов;
- ограничение: не более трёх материалов одному читателю;
- штраф за просрочку печатной книги;
- отчёт по типам материалов;
- загрузка и сохранение данных в `data/library.txt`;
- обработка ошибок и пользовательских исключений;
- отдельный класс тестирования основных операций.

## Архитектура

- `library.model` — модели материалов и интерфейс выдачи;
- `library.service` — правила работы библиотеки;
- `library.storage` — сохранение и загрузка данных;
- `library.util` — обобщённое хранилище и вывод коллекций;
- `library.exception` — собственное исключение;
- `library.app` — консольное меню.

`Material` является абстрактным базовым классом. `PrintedBook` и `EBook`
являются его наследниками. Интерфейс `Borrowable` описывает выдачу и возврат.

## Запуск в IntelliJ IDEA

Открыть папку проекта и запустить класс:

`src/main/java/library/app/Main.java`

Для запуска тестов запустить класс:

`src/test/java/library/test/LibraryTest.java`

## Запуск через терминал

```bash
javac -encoding UTF-8 -d out $(find src/main/java src/test/java -name "*.java")
java -cp out library.app.Main
java -cp out library.test.LibraryTest
```

## Git

Основные команды:

```bash
git init
git status
git add .
git commit -m "chore: create project structure"
git log --oneline
git branch -M main
git remote add origin https://github.com/USERNAME/REPOSITORY.git
git push -u origin main
```

В проекте предусмотрены содержательные коммиты с сообщениями в формате
Conventional Commits:

- `chore: create project structure`;
- `feat: add library domain and borrowing rules`;
- `feat: add console menu and file storage`.
