/*import java.util.*;

public class LibraryManagement extends Document {
    
    /* 
    //public addDocument 
    public boolean isLookupTitle(String Title) {
        return super.libMap.containsKey(Title);
    }

    public boolean isLookupAuthor(String Author) {
        for (HashMap<String, Integer> temp : super.libMap.values()) {
            if (temp.containsKey(Author)) {
                return true;
            }
        }
        return false;
    }

    public void addDocumentToLib(Document document) {
        super.addDocument(document);
    }

    public void removeWordFromLib(Document document) {
        super.removeDocument(document);
    }

    public void editDocumentFromLib(Document currentDocument, String newAuthor) {
        Document newDocument = new Document(currentDocument.getTitle(),newAuthor, currentDocument.getQuantity());
        super.editDocument(currentDocument, newDocument);
    }
    
    public void editDocumentFromLib(Document currentDocument, int newQuantity) {
        Document newDocument = new Document(currentDocument.getTitle(), currentDocument.getAuthor(), newQuantity);
        super.editDocument(currentDocument, newDocument);
    }

    public void editDocumentFromLib(Document currentDocument, String newAuthor, int newQuantity) {
        Document newDocument = new Document(currentDocument.getTitle(),newAuthor, newQuantity);
        super.editDocument(currentDocument, newDocument);
    }
        
}*/