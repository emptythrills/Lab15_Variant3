package library.model;

import library.exception.InvalidLibraryOperationException;

public interface Borrowable {
    void borrow(String reader) throws InvalidLibraryOperationException;

    double returnMaterial(int overdueDays)
            throws InvalidLibraryOperationException;

    boolean isAvailable();
}
