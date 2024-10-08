import java.util.*;

public class UserManagement {
    public List<User> listUser = new ArrayList<>();

    public void addUser(User user) {
        listUser.add(user);
    }

    // kiểm tra xem user có dữ liệu chưa
    // kiểm tra điều kiện để cho mượn
    public void borrowDocument(User user, Document document) {
        if (!listUser.contains(user)) {
            this.addUser(user);
        }
        int number = document.getQuantity();
        if (number >= 1) {
            user.addBorrowedDocuments(document);
            user.setBorrowStatus(true);
            document.setQuantity(number - 1);
        }
        
    } 

    public void returnDocument(User user, Document document) {
        int number = document.getQuantity();
        if (listUser.contains(user)) {
            user.removeBorrowedDocuments(document);
            document.setQuantity(number - 1);
        }
        if (user.checkEmptyBorrowedDocuments(document)) {
            user.setBorrowStatus(false);
        }
    }

}