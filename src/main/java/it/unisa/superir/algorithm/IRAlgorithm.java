package it.unisa.superir.algorithm;

import it.unisa.superir.model.Vocabulary;

@FunctionalInterface
public interface IRAlgorithm {
    double[] getValues(String string, Vocabulary vocabulary);
}
