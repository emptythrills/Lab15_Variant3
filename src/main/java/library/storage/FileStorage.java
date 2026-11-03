package library.storage;

import library.model.EBook;
import library.model.Material;
import library.model.PrintedBook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FileStorage {
    private FileStorage() {
    }

    public static List<Material> load(String fileName) throws IOException {
        List<Material> materials = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName),
                        StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (!line.isBlank()) {
                    materials.add(parse(line, lineNumber));
                }
            }
        }

        return materials;
    }

    public static void save(String fileName, Collection<Material> materials)
            throws IOException {
        Path path = Path.of(fileName);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName),
                        StandardCharsets.UTF_8))) {
            for (Material material : materials) {
                writer.write(material.toFileLine());
                writer.newLine();
            }
        }
    }

    private static Material parse(String line, int lineNumber)
            throws IOException {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 6) {
            throw new IOException("Неверный формат строки " + lineNumber);
        }

        String type = parts[0];
        String id = parts[1];
        String title = parts[2];
        String extra = parts[3];
        String borrower = parts[4];

        if (!parts[5].equals("0") && !parts[5].equals("1")) {
            throw new IOException("Неверный статус строки " + lineNumber);
        }

        boolean available = parts[5].equals("1");
        Material material;

        if (type.equals("P")) {
            material = new PrintedBook(id, title, extra);
        } else if (type.equals("E")) {
            material = new EBook(id, title, extra);
        } else {
            throw new IOException("Неизвестный тип строки " + lineNumber);
        }

        material.restoreState(borrower, available);
        return material;
    }
}
