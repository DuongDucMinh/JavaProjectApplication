import java.util.*;

public class User {
    private String id;
    private String name;
    private String passWord;
    private boolean borrowStatus;
    private List<Document> borrowedDocuments;

    public User(String id) {
        this.name = "anonymous";
        this.id = id;
        this.passWord = "888888";
        this.borrowStatus = false;
        this.borrowedDocuments = new ArrayList<>();
    }

    public User(String id, String name, String passWord) {
        this.name = name;
        this.id = id;
        this.passWord = passWord;
        this.borrowStatus = false;
        this.borrowedDocuments = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setBorrowStatus(boolean borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public void addBorrowedDocuments(Document document) {
        borrowedDocuments.add(document);
    }

    public void removeBorrowedDocuments(Document document) {
        borrowedDocuments.remove(document);
    }

    public boolean checkEmptyBorrowedDocuments(Document document) {
        return borrowedDocuments.isEmpty();
    }
 
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassWord() {
        return passWord;
    }

    public Boolean getBorrowStatus() {
        return borrowStatus;
    }

    public boolean checkReEnterPassWord(String otherPassWord) {
        return passWord.equals(otherPassWord);
    }

    public void printInfoUser() {
        System.out.println("Id: " + id + "\nTen nguoi dung: " + name + "\nTrang thai muon sach: ");
        if (borrowStatus) {
            System.out.println("Dang muon");
        } else {
            System.out.println("Khong muon");
        }
    }

    public void printBorrowDocument() {
        System.out.println("Danh sach tai lieu dang muon: ");
        for (Document borrowDoc : borrowedDocuments) {
            borrowDoc.printInfoDoc();
        }
    }
}