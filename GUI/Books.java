package GUI;

public class Books {

    private int id;
    private String title;
    private String author;
    private String status;
    private int addedBy;
    private String dateAdded;

    public Books(int id, String title, String author, String status, int addedBy, String dateAdded) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.addedBy = addedBy;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public int getAddedBy() {
        return addedBy;
    }

    public String getDateAdded() {
        return dateAdded;
    }
}
