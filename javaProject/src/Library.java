import java.util.*;

public class Library { 
    private List<Document> documents;

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }

    public void editDocument(Document oldDocument, Document newDocument) {
        int index = documents.indexOf(oldDocument);
        if (index != -1) {
            documents.set(index, newDocument);
        }
    }

    public void editDocument(Document currentDocument, String newAuthor, String newGenre, int newQuantity) {
        for (Document doc : documents) {
            if (doc.equals(currentDocument)) {
                doc.setAuthor(newAuthor);
                doc.setGenre(newGenre);
                doc.setQuantity(newQuantity);
            }
        }
    }

    public void editDocumentAuthor(Document currentDocument, String newAuthor) {
        for (Document doc : documents) {
            if (doc.equals(currentDocument)) {
                doc.setAuthor(newAuthor);
            }
        }
    }

    public void editDocumentGenre(Document currentDocument, String newGenre) {
        for (Document doc: documents) {
            if (doc.equals(currentDocument)) {
                doc.setGenre(newGenre);
            }
        }
    }

    public void editDocumentGenre(Document currentDocument, int newQuantity) {
        for (Document doc: documents) {
            if (doc.equals(currentDocument)) {
                doc.setQuantity(newQuantity);
            }
        }
    }

    public List<Document> findDocuments(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getAuthor().toLowerCase().contains(keyWord.toLowerCase()) ||
                doc.getTitle().toLowerCase().contains(keyWord.toLowerCase()) ||
                doc.getGenre().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
                }
        }
        return res;
    }

    public List<Document> findDocTitle(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

    public List<Document> findDocAuthor(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getAuthor().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

    public List<Document> findDocGenre(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getGenre().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

}