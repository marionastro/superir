package it.unisa.superir.algorithm;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;

public class TFIDF implements IRAlgorithm {
    private final Folder folder;

    public TFIDF(Folder folder) {
        this.folder = folder;
    }

    @Override
    public double getWeight(String term) {
        int folderSize  = folder.getSize();
        int folderCount = 0;

        for (Document d : folder.getDocuments())
            folderCount += d.getBody().containsIgnoreCase(term) ? 1 : 0;

        return getIDF(folderSize, folderCount);
    }

    public double getIDF(int total, int times) {
        if (times == 0 || total == times)
            return 0.0d;

        return Math.log10((double) total / times);
    }
}
