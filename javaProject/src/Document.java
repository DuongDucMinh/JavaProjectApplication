
public class Document {
    private String title;
    private String author;
    private String genre;
    private int quantity;

    public Document(String title, String author, String genre, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

    public Document(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = 1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void printInfoDoc() {
        System.out.println("Ten tai lieu: " + title + "\n" + "Ten tac gia: " + author + "\nThe loai: " + genre);
    }

}