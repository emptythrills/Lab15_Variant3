package library.util;

import java.util.Collection;

public final class ConsolePrinter {
    private ConsolePrinter() {
    }

    public static <T> void printCollection(Collection<T> collection) {
        if (collection.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }

        for (T item : collection) {
            System.out.println(item);
        }
    }
}
