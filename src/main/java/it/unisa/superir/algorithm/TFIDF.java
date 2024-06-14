package it.unisa.superir.algorithm;

import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import it.unisa.superir.model.Vocabulary;

/**
 * Implementation of TF-IDF IR Algorithm, the score is affected by document length
 * and frequency of the word in the entire folder.
 * 
 * @author Gruppo5
 */
public class TFIDF implements IRAlgorithm {
    private final Folder folder;

    /**
     * function to associate a folder to the algorithm.
     * @param folder    the folder to use the algorithm on.
     */
    public TFIDF(Folder folder) {
        this.folder = folder;
    }

    /**
     * Function to run to calculate the IR score of a word given a set of words.
     * 
     * @param string        string to calculate the score of.
     * @param vocabulary    set of words used to calculate the scores of the string.
     * @return              the string scores.
     */
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

    /**
     * Function to run to obtain the weight of a given word
     * the method calculates the number of documents in which the word appears and
     * the total number of documents in the folder.
     * 
     * @param term      the term to calculate the idf on
     * @return          the idf of term.
     */
    private double getWeight(String term) {
        int folderSize  = folder.getSize();
        int folderCount = 0;

        for (Document d : folder.getDocuments())
            folderCount += (d.getTitle().containsIgnoreCase(term) ||
                            d.getBody().containsIgnoreCase(term))
                        ? 1 : 0;

        return getIDF(folderSize, folderCount);
    }

    /**
     * Function to run to obtain the idf of a word
     * if the folder is empty or the word never occurs, returns 0
     * else return log10 of the ratio between total and times.
     * 
     * @param total         total number of documents in the folder.
     * @param times         number of documents containing a given word.
     * @return              the IDF.
     */
    private double getIDF(int total, int times) {
        if (times == 0 || total == times)
            return 0.0d;

        return Math.log10((double) total / times);
    }
}
