package it.unisa.superir.algorithm;

import it.unisa.superir.model.Vocabulary;

/**
 * Standard IR algorithm the score is only affected by word frequency in the document.
 * 
 * @author Gruppo5
 */
public class StandardIR implements IRAlgorithm {

    /**
     * Function to run to calculate the IR score of a word given a set of words.
     * 
     * @param string        string to calculate the scores of.
     * @param vocabulary    set of words used to calculate the scores of the string.
     * @return              the string scores.
     */
    @Override
    public double[] getValues(String string, Vocabulary vocabulary) {
        double[] values = new double[vocabulary.getLength()];
        String[] tokens = string.toLowerCase().split("[^A-Za-zÀ-ú0-9]+");

        for (String token : tokens) {
            int index = vocabulary.getIndex(token);

            if (index >= 0) {
                values[index]++;
            }
        }

        return values;
    }
}
