package library.service;

import library.exception.InvalidLibraryOperationException;
import library.model.Material;
import library.util.Storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private static final int MAX_BORROWED = 3;
    private Storage<Material> storage = new Storage<>();

    public void addMaterial(Material material)
            throws InvalidLibraryOperationException {
        if (findById(material.getId()) != null) {
            throw new InvalidLibraryOperationException(
                    "Материал с таким идентификатором уже существует");
        }
        storage.add(material);
    }

    public List<Material> getAll() {
        return storage.getAll();
    }

    public Material findById(String id) {
        return storage.find(material -> material.getId().equalsIgnoreCase(id));
    }

    public List<Material> search(String text) {
        return search(text, false);
    }

    public List<Material> search(String text, boolean onlyAvailable) {
        List<Material> result = new ArrayList<>();
        String query = text.toLowerCase();

        for (Material material : storage.getAll()) {
            boolean matches = material.getTitle().toLowerCase().contains(query);
            if (matches && (!onlyAvailable || material.isAvailable())) {
                result.add(material);
            }
        }

        return result;
    }

    public void borrow(String id, String reader)
            throws InvalidLibraryOperationException {
        Material material = findRequired(id);
        if (countBorrowedBy(reader) >= MAX_BORROWED) {
            throw new InvalidLibraryOperationException(
                    "Нельзя выдать больше трёх материалов одному читателю");
        }
        material.borrow(reader);
    }

    public double returnMaterial(String id, int overdueDays)
            throws InvalidLibraryOperationException {
        return findRequired(id).returnMaterial(overdueDays);
    }

    public Map<String, Integer> countByType() {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Material material : storage.getAll()) {
            String type = material.getType();
            result.put(type, result.getOrDefault(type, 0) + 1);
        }
        return result;
    }

    private Material findRequired(String id)
            throws InvalidLibraryOperationException {
        Material material = findById(id);
        if (material == null) {
            throw new InvalidLibraryOperationException(
                    "Материал с таким идентификатором не найден");
        }
        return material;
    }

    private int countBorrowedBy(String reader) {
        int count = 0;
        for (Material material : storage.getAll()) {
            if (!material.isAvailable()
                    && material.getBorrower().equalsIgnoreCase(reader)) {
                count++;
            }
        }
        return count;
    }
}
