package it.unisa.superir.service;

import it.unisa.superir.algorithm.IRAlgorithm;
import it.unisa.superir.math.CosineSimilarity;
import it.unisa.superir.model.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

public class QueryExecutionService extends Service<QueryExecution> {
    private final Folder folder;
    private final String query;
    private final ObservableList<File> stopWordsFiles;
    private final IRAlgorithm algorithm;
    private final double titleRilevance;

    public QueryExecutionService(Folder folder, String query, ObservableList<File> stopWordsFiles, IRAlgorithm algorithm, double titleRilevance) {
        this.folder = folder;
        this.query = query;
        this.stopWordsFiles = stopWordsFiles;
        this.algorithm = algorithm;
        this.titleRilevance = titleRilevance;
    }

    @Override
    protected Task<QueryExecution> createTask() {
        return new Task<QueryExecution>() {
            @Override
            protected QueryExecution call() throws Exception {
                QueryExecution queryExecution = new QueryExecution(folder, query, stopWordsFiles, algorithm, titleRilevance);

                for (File file : stopWordsFiles) {
                    StopWordsFile stopWordsFile = new StopWordsFile(file);
                    folder.filter(stopWordsFile);
                }

                Vocabulary vocabulary = folder.getVocabulary();

                for (Document document : folder.getDocuments()) {
                    double[] queryValues = algorithm.getValues(query, vocabulary);
                    double[] titleValues = algorithm.getValues(document.getTitle().getJoining(), vocabulary);
                    double[] bodyValues  = algorithm.getValues(document.getBody().getJoining(), vocabulary);

                    Score score = new Score(
                            CosineSimilarity.compute(queryValues, titleValues) * titleRilevance,
                            CosineSimilarity.compute(queryValues, bodyValues) * (1 - titleRilevance)
                    );

                    queryExecution.setScore(document, score);
                }

                return queryExecution;
            }
        };
    }
}
