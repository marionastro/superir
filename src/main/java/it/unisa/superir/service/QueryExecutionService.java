package it.unisa.superir.service;

import it.unisa.superir.algorithm.IRAlgorithm;
import it.unisa.superir.math.CosineSimilarity;
import it.unisa.superir.model.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;

/**
 * Service used to execute a query.
 * @author Gruppo5
 */
public class QueryExecutionService extends Service<QueryExecution> {
    private final Folder folder;
    private final String query;
    private final ObservableList<File> stopWordsFiles;
    private final IRAlgorithm algorithm;
    private final double titleRelevance;

    /**
     * Service that calculate the score of each document in a given folder,
     * It filters out from all docs vocabularies all the words present in the stopWords file vocabularies,
     * then, for each document, it calculates the score using CosineSimilarity
     * it then returns the execution query containing all the scores.
     * 
     * @param folder    the folder in which to extract the documents.
     * @param query     the query used to calculate score.
     * @param stopWordsFiles    files containing stopwords.
     * @param algorithm         the IR algorithm to apply to calculate score.
     * @param titleRelevance    the weight that words in title have in the score calculation.
     */
    public QueryExecutionService(Folder folder, String query, ObservableList<File> stopWordsFiles, IRAlgorithm algorithm, double titleRelevance) {
        this.folder = folder;
        this.query = query;
        this.stopWordsFiles = stopWordsFiles;
        this.algorithm = algorithm;
        this.titleRelevance = titleRelevance;
    }

    @Override
    protected Task<QueryExecution> createTask() {
        return new Task<QueryExecution>() {
            @Override
            protected QueryExecution call() throws Exception {
                QueryExecution queryExecution = new QueryExecution(folder, query, stopWordsFiles, algorithm, titleRelevance);
                Vocabulary vocabulary = folder.getVocabulary();

                for (File file : stopWordsFiles) {
                    StopWordsFile stopWordsFile = new StopWordsFile(file);
                    vocabulary = vocabulary.filter(stopWordsFile.getVocabulary());
                }

                double[] queryValues = algorithm.getValues(query, vocabulary);

                for (Document document : folder.getDocuments()) {
                    double[] titleValues = algorithm.getValues(document.getTitle().getJoining(), vocabulary);
                    double[] bodyValues  = algorithm.getValues(document.getBody().getJoining(), vocabulary);

                    Score score = new Score(
                            CosineSimilarity.compute(queryValues, titleValues) * titleRelevance,
                            CosineSimilarity.compute(queryValues, bodyValues) * (100 - titleRelevance)
                    );

                    queryExecution.setScore(document, score);
                }

                return queryExecution;
            }
        };
    }
}
