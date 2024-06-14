package it.unisa.superir.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a generic folder.
 * 
 * @author Gruppo5
 */
public class Folder {

    /**
     *
     */
    public final String name;
    private final Set<Document> documents;
    private Vocabulary vocabulary;

    /**
     * Constructor given the name of the folder.
     * 
     * @param name  the name of the folder.
     */
    public Folder(String name) {
        this.name = name;
        this.documents = new HashSet<>();
        this.vocabulary = new Vocabulary();
    }

    /**
     * Function to run to get the number of document in the folder.
     *
     * @return the number of documents in the folder.
     */
    public int getSize() {
        return documents.size();
    }

    /**
     * Function to run to get the set of document contained in the folder.
     * 
     * @return the set of documents contained in the folder.
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * Function to run to add a new document into the folder and
     * join the folder vocabulary with the document one.
     * 
     * @param document the document to add to the Folder.
     * @return false if the document is already present, true otherwise.
     */
    public boolean add(Document document) {
        if (documents.add(document)) {
            vocabulary = vocabulary.join(document.getVocabulary());
            return true;
        }

        return false;
    }


    /**
     * Function to run to get the total number of words contained in the document of the folder.
     * 
     * @return the number of words in the folder.
     */
    public int getTotalWords() {
        return getDocuments().stream().mapToInt(Document::getLength).sum();
    }

    /**
     * Function to run to get a mapping between a word preent in a folder document
     * and the number of times it shows up in all the documents of the folder. 
     * 
     * @return a map containing words and how many times they appeared in the folder.
     */
    public Map<String, Long> getOccurrences() {
        return new HashMap<String, Long>() {
            {
                for (Document document : getDocuments()) {
                    document.getTitle().getOccurrences().forEach((w, o) -> merge(w, 1L, (k, v) -> v + o));
                    document.getBody().getOccurrences().forEach((w, o)  -> merge(w, 1L, (k, v) -> v + o));
                }

            }
        };
    }

    /**
     * Function to run to get the average number of words per document.
     * 
     * @return the average number of words per document.
     */
    public int getAverageDocumentsWords() {
        return getTotalWords() / documents.size();
    }

    /**
     *
     * Function to run to get the set of words used in the folder documents.
     * @return the current vocabulary of the folder.
     */
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /**
     * Function to run to get the folder name.
     * @return the name of the folder.
     */
    public String getName() {
        return name;
    }
}
