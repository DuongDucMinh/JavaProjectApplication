import java.util.*;

public class UserManagement {
    private List<User> listUser;

    public UserManagement() {
        listUser = new ArrayList<>();
    }

    public void addUser(User user) {
        listUser.add(user);
    }

    // kiểm tra xem user có dữ liệu chưa
    // kiểm tra điều kiện để cho mượn
    // trước khi cho mượn và trả phải nhập id và password
    public void borrowDocument(User user, Document document) {
        if (!listUser.contains(user)) {
            this.addUser(user);
        }
        int number = document.getQuantity();
        if (number >= 1) {
            user.addBorrowedDocuments(document);
            user.setBorrowStatus(true);
            document.setQuantity(number - 1);
        } else {
            System.out.println("This document is out of stock!");
        }
    } 

    public void returnDocument(User user, Document document) {
        int number = document.getQuantity();
        if (listUser.contains(user)) {
            user.removeBorrowedDocuments(document);
            document.setQuantity(number - 1);
        } else {
            System.out.println("This user does not exist!");
        }
        if (user.checkEmptyBorrowedDocuments(document)) {
            user.setBorrowStatus(false);
        }
    }

    public void printListUser() {
        System.out.println("Danh sach thanh vien: ");
        for (User user : listUser) {
            user.printInfoUser();
        }
    }
}