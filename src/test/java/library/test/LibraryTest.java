package library.test;

import library.exception.InvalidLibraryOperationException;
import library.model.PrintedBook;
import library.service.LibraryService;

public class LibraryTest {
    public static void main(String[] args) throws Exception {
        LibraryService library = new LibraryService();
        library.addMaterial(new PrintedBook("t1", "Тестовая книга", "Автор"));

        library.borrow("t1", "Иван");

        try {
            library.borrow("t1", "Пётр");
            throw new AssertionError("Повторная выдача должна быть запрещена");
        } catch (InvalidLibraryOperationException e) {
            System.out.println("Проверка повторной выдачи пройдена");
        }

        double fine = library.returnMaterial("t1", 3);
        if (fine != 6.0) {
            throw new AssertionError("Неверный расчёт штрафа");
        }

        if (library.search("Тестовая").size() != 1) {
            throw new AssertionError("Ошибка поиска");
        }

        System.out.println("Все проверки пройдены");
    }
}
