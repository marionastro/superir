package it.unisa.superir.algorithm;

import it.unisa.superir.model.Vocabulary;

/**
 * This interface represents a generic IR algorithm.
 * 
 * @author Gruppo5
 */
@FunctionalInterface
public interface IRAlgorithm {

    /**
     * Function to run to calculate the IR scores of a word given a set of words.
     * 
     * @param string        string to calculate the score of.
     * @param vocabulary    set of words used to calculate the scores of the string.
     * @return              the string scores.
     */
    double[] getValues(String string, Vocabulary vocabulary);
}
