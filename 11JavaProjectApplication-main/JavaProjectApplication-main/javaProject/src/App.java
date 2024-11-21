import java.util.*;

public class App {
    public static void main(String[] args) {
        Library library = new Library();
        UserManagement userManagement = new UserManagement();
        Scanner sc = new Scanner(System.in);
        int option, type, quantity;
        String idDoc, name, author, genre, email, passWord;
        List<Document> resultSearch;
        do {
            Document document = new Document(null, null, null, null);
            User user = new User(null, null, null);
            System.out.println("""
                               Welcome to My Application!
                               [0] Exit
                               [1] Add Document
                               [2] Remove Document
                               [3] Update Document
                               [4] Find Document
                               [5] Display Document
                               [6] Add User
                               [7] Borrow Document
                               [8] Return Document
                               [9] Display User Info""");
            
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.print("Nhap ma so tai lieu: ");
                    idDoc = sc.nextLine();
                    document.setidDoc(idDoc);
                    System.out.print("Nhap ten tai lieu: ");
                    name = sc.nextLine();
                    document.setTitle(name);
                    System.out.print("Nhap ten tac gia: ");
                    author = sc.nextLine();
                    document.setAuthor(author);
                    System.out.print("Nhap ten the loai: ");
                    genre = sc.nextLine();
                    document.setGenre(genre);
                    System.out.print("Nhap so luong: ");
                    quantity = sc.nextInt();
                    document.setQuantity(quantity);

                    library.addDocument(document);

                    System.out.println("Them tai lieu thanh cong!");

                    break;

                case 2:
                    System.out.print("Nhap ma so tai lieu muon xoa: ");
                    idDoc = sc.nextLine();
                    if (library.lookUpDoc(idDoc)) {
                        library.removeDocument(idDoc);
                        System.out.println("Document deletion successful!");
                    } else {
                        System.out.println("This document is not available in the library!");
                    }

                    break;

                case 3:
                    System.out.println("""
                                        What type of update do you want?
                                        [1] Title
                                        [2] Author
                                        [3] Genre
                                        [4] Quantity
                                        [5] All of above
                                        """);

                    type = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhap ma so tai lieu muon update: ");
                    idDoc = sc.nextLine();
                    if (library.lookUpDoc(idDoc)) {
                        switch (type) {
                            case 1:
                                System.out.print("Enter new Title: ");
                                name = sc.nextLine();
                                library.editDocumentTitle(idDoc, name);
                                break;

                            case 2:
                                System.out.print("Enter new Author: ");
                                author = sc.nextLine();
                                library.editDocumentAuthor(idDoc, author);
                                break;

                            case 3:
                                System.out.print("Enter new Genre: ");
                                genre = sc.nextLine();
                                library.editDocumentGenre(idDoc, genre);
                                break;

                            case 4:
                                System.out.print("Enter new Quantity: ");
                                quantity = sc.nextInt();
                                library.editDocumentQuantity(idDoc, quantity);
                                break;

                            case 5:
                                System.out.print("Enter new Title: ");
                                name = sc.nextLine();
                                System.out.print("Enter new Author: ");
                                author = sc.nextLine();
                                System.out.print("Enter new Genre: ");
                                genre = sc.nextLine();
                                System.out.print("Enter new Quantity: ");
                                quantity = sc.nextInt();
                                library.editDocument(idDoc, name, author, genre, quantity);
                                break;

                            default:
                                throw new AssertionError();
                        }
                        System.out.println("Document update successful!");
                    } else {
                        System.out.println("This document is not available in the library!");
                    }

                    break;

                case 4:
                    System.out.println("""
                                        Which category do you want to search by?
                                        [1] Id
                                        [2] Title
                                        [3] Author
                                        [4] Genre
                                        [5] All of above
                                        """);
                    type = sc.nextInt();
                    sc.nextLine();
                    switch (type) {
                        case 1:
                            System.out.print("Enter id of Document: ");
                            idDoc = sc.next();
                            if (library.lookUpDoc(idDoc)) {
                                library.findDocId(idDoc).printInfoDoc();
                            } else {
                                System.out.println("This document is not available in the library!");
                            }
                            break;

                        case 2:
                            System.out.print("Enter key word to find Title of Document: ");
                            name = sc.nextLine();
                            resultSearch = library.findDocTitle(name);
                            if (resultSearch.isEmpty()) {
                                System.out.println("Document doesn't exist!");
                            } else {
                                for (Document doc : resultSearch) {
                                    doc.printInfoDoc();
                                }
                            }
                            break;

                        case 3:
                            System.out.print("Enter key word to find Author of Document: ");
                            author = sc.nextLine();
                            resultSearch = library.findDocAuthor(author);
                            if (resultSearch.isEmpty()) {
                                System.out.println("Document doesn't exist!");
                            } else {
                                for (Document doc : resultSearch) {
                                    doc.printInfoDoc();
                                }
                            }
                            break;

                        case 4:
                            System.out.print("Enter key word to find Genre of Document: ");
                            genre = sc.nextLine();
                            resultSearch = library.findDocGenre(genre);
                            if (resultSearch.isEmpty()) {
                                System.out.println("Document doesn't exist!");
                            } else {
                                for (Document doc : resultSearch) {
                                    doc.printInfoDoc();
                                }
                            }
                            break;

                        case 5:
                            System.out.print("Enter key word: ");
                            String keyWord = sc.nextLine();
                            resultSearch = library.findAll(keyWord);
                            if (resultSearch.isEmpty()) {
                                System.out.println("Document doesn't exist!");
                            } else {
                                for (Document doc : resultSearch) {
                                    doc.printInfoDoc();
                                }
                            }
                            break;

                        default:
                            throw new AssertionError();
                    }
                    break;

                case 5:
                    System.out.println("Danh sach tai lieu co trong thu vien:");
                    library.printDocument();
                    break;

                case 6:
                    System.out.print("Enter your email: ");
                    email = sc.nextLine();
                    user.setId(email);
                    System.out.print("Enter your full name: ");
                    name = sc.nextLine();
                    user.setName(name);
                    System.out.print("Enter pass word: ");
                    passWord = sc.nextLine();
                    user.setPassWord(passWord);
                    System.out.print("Re-enter pass word: ");
                    do {
                        passWord = sc.nextLine();
                    } while (user.checkReEnterPassWord(passWord));

                    userManagement.addUser(user);
                    System.out.println("Them tai khoan thanh cong!");
                    break;

                case 7:

                    break;

                case 8: 

                    break;

                case 9:

                    break;

                default:
                    throw new AssertionError();
            }
        } while (true);

        //System.out.println("Goodbye!");
        //sc.close();
    }
}
