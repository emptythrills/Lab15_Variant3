package library.model;

import library.exception.InvalidLibraryOperationException;

public abstract class Material implements Borrowable {
    private String id;
    private String title;
    private boolean available = true;
    private String borrower = "";

    protected Material(String id, String title) {
        if (id == null || id.isBlank() || title == null || title.isBlank()) {
            throw new IllegalArgumentException("Идентификатор и название обязательны");
        }
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrower() {
        return borrower;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void borrow(String reader) throws InvalidLibraryOperationException {
        if (reader == null || reader.isBlank()) {
            throw new InvalidLibraryOperationException("Читатель не указан");
        }
        if (!available) {
            throw new InvalidLibraryOperationException(
                    "Материал уже выдан читателю " + borrower);
        }
        borrower = reader;
        available = false;
    }

    @Override
    public double returnMaterial(int overdueDays)
            throws InvalidLibraryOperationException {
        if (available) {
            throw new InvalidLibraryOperationException(
                    "Материал сейчас не выдан");
        }
        if (overdueDays < 0) {
            throw new InvalidLibraryOperationException(
                    "Количество дней просрочки не может быть отрицательным");
        }

        double fine = calculateFine(overdueDays);
        borrower = "";
        available = true;
        return fine;
    }

    public void restoreState(String borrower, boolean available) {
        this.borrower = borrower == null ? "" : borrower;
        this.available = available;
    }

    public String getInfo() {
        String status = available ? "доступен" : "выдан: " + borrower;
        return getType() + " | " + id + " | " + title + " | " + status;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public abstract String getType();

    public abstract double calculateFine(int overdueDays);

    public abstract String toFileLine();
}
