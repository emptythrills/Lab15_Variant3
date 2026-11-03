package library.model;

public class EBook extends Material {
    private String format;

    public EBook(String id, String title, String format) {
        super(id, title);
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String getType() {
        return "Электронное издание";
    }

    @Override
    public double calculateFine(int overdueDays) {
        return 0;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | формат: " + format;
    }

    @Override
    public String toFileLine() {
        return "E|" + getId() + "|" + getTitle() + "|" + format
                + "|" + getBorrower() + "|" + (isAvailable() ? "1" : "0");
    }
}
