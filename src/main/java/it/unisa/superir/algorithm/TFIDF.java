package it.unisa.superir.algorithm;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import it.unisa.superir.model.Vocabulary;

public class TFIDF implements IRAlgorithm {
    private final Folder folder;

    public TFIDF(Folder folder) {
        this.folder = folder;
    }

    @Override
    public double[] getValues(String string, Vocabulary vocabulary) {
        double[] values = new double[vocabulary.getLength()];
        String[] tokens = string.toLowerCase().split("[^A-zÀ-ú0-9]+");

        for (String token : tokens) {
            int index = vocabulary.getIndex(token);

            if (index >= 0) {
                values[index]++;
            }
        }

        for (int i = 0; i < values.length; i++) {
            String token = vocabulary.getToken(i);
            values[i] = (values[i] / tokens.length) * getWeight(token);
        }

        return values;
    }

    private double getWeight(String term) {
        int folderSize  = folder.getSize();
        int folderCount = 0;

        for (Document d : folder.getDocuments())
            folderCount += d.getBody().containsIgnoreCase(term) ? 1 : 0;

        return getIDF(folderSize, folderCount);
    }

    private double getIDF(int total, int times) {
        if (times == 0 || total == times)
            return 0.0d;

        return Math.log10((double) total / times);
    }
}
