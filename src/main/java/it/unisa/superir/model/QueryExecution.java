package it.unisa.superir.model;

import it.unisa.superir.algorithm.IRAlgorithm;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represent an execution of query using an IR algorithm on a folder.
 * @author Gruppo5
 */
public class QueryExecution {
    private final Folder folder;
    private final String query;
    private final ObservableList<File> stopWordsFiles;
    private final IRAlgorithm algorithm;
    private final double titleRelevance;
    private final Map<Document, Score> scores;

    /**
     * Constructor.
     * 
     * @param folder        the folder to perform the algorithm on.
     * @param query         the query to use for the IR algorithm.
     * @param stopWordsFiles    a List of file containing stopwords.
     * @param algorithm       the IR algorithm to use.
     * @param titleRelevance    the weight that has to be given for title words.
     */
    public QueryExecution(Folder folder, String query, ObservableList<File> stopWordsFiles, IRAlgorithm algorithm, double titleRelevance) {
        this.folder = folder;
        this.query = query;
        this.stopWordsFiles = stopWordsFiles;
        this.algorithm = algorithm;
        this.titleRelevance = titleRelevance;
        this.scores = new HashMap<>();
    }

    /**
     * Function to run to assign a score to a specific Document.
     * 
     * @param document  the document that has to be assign a score
     * @param score     the score to assign to the document
     * @return          null if the document did not have a score yet, its previous score otherwise.
     */
    public Score setScore(Document document, Score score) {
        return scores.put(document, score);
    }

    /**
     * Function to run obtain the score of a given Document as a Score Object.
     * @param document  document whose score has to be extracted.
     * @return  the score of the document.
     */
    public Score getScore(Document document) {
        return scores.get(document);
    }

    // Reverse Sorting poichè i documenti vengono aggiunti in pila.

    /**
     * Function to run to order in a list, using a reverse sorting, 
     * the folder documents, the first element is the document with the higher score.
     * @return the ordered list of documents.
     */
    public List<Document> getSorted() {
        return scores.keySet().stream()
                .sorted((d1, d2) -> scores.get(d2).compareTo(scores.get(d1)))
                .collect(Collectors.toList());
    }

    /**
     * Function to run to obtain the folder the algorithm is executed on.
     * @return  the folder used in the algorithm.
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Function to run to obtain the query the IR algorithm is using.
     * @return  the query used in the algorithm.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Function to run to obtain the list of files containing stop words that algorithm is considering.
     * @return  the list of stopwords files used in the algorithm.
     */
    public ObservableList<File> getStopWordsFiles() {
        return stopWordsFiles;
    }

    /**
     * Function to run to obtain the algorithm used.
     * @return  the algorithm used in the execution of the query.
     */
    public IRAlgorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Function to run to obtain the weight modifier used for the words in a document title.
     * @return  the weight of title words used in the algorithm.
     */
    public double getTitleRelevance() {
        return titleRelevance;
    }
}
