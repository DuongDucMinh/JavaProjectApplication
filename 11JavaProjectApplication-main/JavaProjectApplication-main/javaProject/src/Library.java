import java.util.*;

public class Library {
    private HashMap<String, Document> docMap;

    public Library() {
        docMap = new HashMap<>();
    }

    public void addDocument(Document document) {
        docMap.put(document.getIdDoc(), document);
    }

    public void removeDocument(String id) {
        docMap.remove(id);
    }

    public void editDocument(String id, String newTitle, String newAuthor, String newGenre, int newQuantity) {
        Document doc = docMap.get(id);
        doc.setTitle(newTitle);
        doc.setAuthor(newAuthor);
        doc.setGenre(newGenre);
        doc.setQuantity(newQuantity);
        docMap.put(id, doc);
    }

    public void editDocumentTitle(String id, String newTitle) {
        Document doc = docMap.get(id);
        doc.setAuthor(newTitle);
        docMap.put(id, doc);
    }

    public void editDocumentAuthor(String id, String newAuthor) {
        Document doc = docMap.get(id);
        doc.setAuthor(newAuthor);
        docMap.put(id, doc);
    }

    public void editDocumentGenre(String id, String newGenre) {
        Document doc = docMap.get(id);
        doc.setGenre(newGenre);
        docMap.put(id, doc);
    }

    public void editDocumentQuantity(String id, int newQuantity) {
        Document doc = docMap.get(id);
        doc.setQuantity(newQuantity);
        docMap.put(id, doc);
    }

    public boolean lookUpDoc(String id) {
        return docMap.containsKey(id);
    }

    public Document findDocId(String id) {
        return docMap.get(id);
    }

    public List<Document> findAll(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : docMap.values()) {
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
        for (Document doc : docMap.values()) {
            if (doc.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

    public List<Document> findDocAuthor(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : docMap.values()) {
            if (doc.getAuthor().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

    public List<Document> findDocGenre(String keyWord) {
        List<Document> res = new ArrayList<>();
        for (Document doc : docMap.values()) {
            if (doc.getGenre().toLowerCase().contains(keyWord.toLowerCase())) {
                res.add(doc);
            }
        }
        return res;
    }

    public void printDocument() {
        for (String key : docMap.keySet()) {
            docMap.get(key).printInfoDoc();
        }
    }
}