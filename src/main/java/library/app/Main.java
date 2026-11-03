package library.app;

import library.exception.InvalidLibraryOperationException;
import library.model.EBook;
import library.model.Material;
import library.model.PrintedBook;
import library.service.LibraryService;
import library.storage.FileStorage;
import library.util.ConsolePrinter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "data/library.txt";

    public static void main(String[] args) {
        LibraryService library = new LibraryService();

        try {
            for (Material material : FileStorage.load(DATA_FILE)) {
                library.addMaterial(material);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл данных не найден. Список пуст.");
        } catch (IOException | InvalidLibraryOperationException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            boolean running = true;

            while (running) {
                printMenu();

                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            ConsolePrinter.printCollection(library.getAll());
                            break;
                        case 2:
                            search(library, scanner, false);
                            break;
                        case 3:
                            search(library, scanner, true);
                            break;
                        case 4:
                            borrow(library, scanner);
                            break;
                        case 5:
                            returnMaterial(library, scanner);
                            break;
                        case 6:
                            addPrintedBook(library, scanner);
                            break;
                        case 7:
                            addEBook(library, scanner);
                            break;
                        case 8:
                            showReport(library);
                            break;
                        case 0:
                            FileStorage.save(DATA_FILE, library.getAll());
                            running = false;
                            System.out.println("Данные сохранены");
                            break;
                        default:
                            System.out.println("Такого пункта меню нет");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число");
                } catch (InvalidLibraryOperationException e) {
                    System.out.println("Ошибка операции: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Ошибка файла: " + e.getMessage());
                } finally {
                    System.out.println("Операция завершена");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Показать материалы");
        System.out.println("2. Поиск по названию");
        System.out.println("3. Поиск доступных материалов");
        System.out.println("4. Выдать материал");
        System.out.println("5. Вернуть материал");
        System.out.println("6. Добавить печатную книгу");
        System.out.println("7. Добавить электронное издание");
        System.out.println("8. Показать отчёт");
        System.out.println("0. Сохранить и выйти");
        System.out.print("Выберите действие: ");
    }

    private static void search(
            LibraryService library,
            Scanner scanner,
            boolean onlyAvailable) {
        System.out.print("Введите текст для поиска: ");
        String query = scanner.nextLine();
        List<Material> result = library.search(query, onlyAvailable);
        ConsolePrinter.printCollection(result);
    }

    private static void borrow(
            LibraryService library,
            Scanner scanner)
            throws InvalidLibraryOperationException {
        System.out.print("Идентификатор материала: ");
        String id = scanner.nextLine();
        System.out.print("Имя читателя: ");
        String reader = scanner.nextLine();
        library.borrow(id, reader);
        System.out.println("Материал выдан");
    }

    private static void returnMaterial(
            LibraryService library,
            Scanner scanner)
            throws InvalidLibraryOperationException {
        System.out.print("Идентификатор материала: ");
        String id = scanner.nextLine();
        System.out.print("Дней просрочки: ");
        int overdueDays = Integer.parseInt(scanner.nextLine());
        double fine = library.returnMaterial(id, overdueDays);
        System.out.println("Материал возвращён. Штраф: " + fine + " руб.");
    }

    private static void addPrintedBook(
            LibraryService library,
            Scanner scanner)
            throws InvalidLibraryOperationException {
        System.out.print("Идентификатор: ");
        String id = scanner.nextLine();
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        library.addMaterial(new PrintedBook(id, title, author));
        System.out.println("Печатная книга добавлена");
    }

    private static void addEBook(
            LibraryService library,
            Scanner scanner)
            throws InvalidLibraryOperationException {
        System.out.print("Идентификатор: ");
        String id = scanner.nextLine();
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Формат: ");
        String format = scanner.nextLine();
        library.addMaterial(new EBook(id, title, format));
        System.out.println("Электронное издание добавлено");
    }

    private static void showReport(LibraryService library) {
        System.out.println("Количество материалов по типам:");
        for (Map.Entry<String, Integer> entry
                : library.countByType().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
