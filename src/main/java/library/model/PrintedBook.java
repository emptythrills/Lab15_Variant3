package library.model;

public class PrintedBook extends Material {
    private String author;

    public PrintedBook(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getType() {
        return "Печатная книга";
    }

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 2.0;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | автор: " + author;
    }

    @Override
    public String toFileLine() {
        return "P|" + getId() + "|" + getTitle() + "|" + author
                + "|" + getBorrower() + "|" + (isAvailable() ? "1" : "0");
    }
}
