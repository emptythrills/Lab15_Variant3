package library.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Storage<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public T find(Predicate<T> condition) {
        for (T item : items) {
            if (condition.test(item)) {
                return item;
            }
        }
        return null;
    }
}
