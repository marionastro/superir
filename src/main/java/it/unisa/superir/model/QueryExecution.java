package it.unisa.superir.model;

import it.unisa.superir.algorithm.IRAlgorithm;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class QueryExecution {
    private final Folder folder;
    private final String query;
    private final ObservableList<File> stopWordsFiles;
    private final IRAlgorithm algorithm;
    private final double titleRelevance;
    private final Map<Document, Score> scores;

    public QueryExecution(Folder folder, String query, ObservableList<File> stopWordsFiles, IRAlgorithm algorithm, double titleRelevance) {
        this.folder = folder;
        this.query = query;
        this.stopWordsFiles = stopWordsFiles;
        this.algorithm = algorithm;
        this.titleRelevance = titleRelevance;
        this.scores = new HashMap<>();
    }

    public Score setScore(Document document, Score score) {
        return scores.put(document, score);
    }

    public Score getScore(Document document) {
        return scores.get(document);
    }

    // Reverse Sorting poichè i documenti vengono aggiunti in pila.
    public List<Document> getSorted() {
        return scores.keySet().stream()
                .sorted((d1, d2) -> scores.get(d2).compareTo(scores.get(d1)))
                .collect(Collectors.toList());
    }

    public Folder getFolder() {
        return folder;
    }

    public String getQuery() {
        return query;
    }

    public ObservableList<File> getStopWordsFiles() {
        return stopWordsFiles;
    }

    public IRAlgorithm getAlgorithm() {
        return algorithm;
    }

    public double getTitleRelevance() {
        return titleRelevance;
    }
}
