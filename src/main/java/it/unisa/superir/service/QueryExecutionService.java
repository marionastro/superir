package it.unisa.superir.service;

import it.unisa.superir.algorithm.IRAlgorithm;
import it.unisa.superir.algorithm.ResultPair;
import it.unisa.superir.math.CosineSimilarity;
import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import it.unisa.superir.model.StopWordsFile;
import it.unisa.superir.model.Vocabulary;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

public class QueryExecutionService extends Service<Map<Document, ResultPair>> {
    private final Folder folder;
    private final String query;
    private final StopWordsFile[] stopWordsFiles;
    private final IRAlgorithm algorithm;
    private final double titleRilevance;

    public QueryExecutionService(Folder folder, String query, StopWordsFile[] stopWordsFiles, IRAlgorithm algorithm, double titleRilevance) {
        this.folder = folder;
        this.query = query;
        this.stopWordsFiles = stopWordsFiles;
        this.algorithm = algorithm;
        this.titleRilevance = titleRilevance;
    }


    @Override
    protected Task<Map<Document, ResultPair>> createTask() {
        return new Task<Map<Document, ResultPair>>() {
            @Override
            protected Map<Document, ResultPair> call() throws Exception {
                HashMap<Document, ResultPair> results = new HashMap<>();

                for (StopWordsFile stopWordsFile : stopWordsFiles)
                    folder.filter(stopWordsFile);

                Vocabulary vocabulary = folder.getVocabulary();

                for (Document document : folder.getDocuments()) {
                    double[] queryValues = algorithm.getValues(query, vocabulary);
                    double[] titleValues = algorithm.getValues(document.getTitle().getJoining(), vocabulary);
                    double[] bodyValues  = algorithm.getValues(document.getBody().getJoining(), vocabulary);

                    ResultPair resultPair = new ResultPair(
                            CosineSimilarity.compute(queryValues, titleValues) * titleRilevance,
                            CosineSimilarity.compute(queryValues, bodyValues) * (1 - titleRilevance)
                    );

                    results.put(document, resultPair);
                }

                return results;
            }
        };
    }
}
