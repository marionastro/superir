package it.unisa.superir.algorithm;

import it.unisa.superir.model.Vocabulary;

public class StandardIR implements IRAlgorithm {
    @Override
    public double[] getValues(String string, Vocabulary vocabulary) {
        double[] values = new double[vocabulary.getLength()];
        String[] tokens = string.toLowerCase().split("[^A-zÀ-ú0-9]+");

        for (String token : tokens) {
            int index = vocabulary.getIndex(token);

            if (index > 0) {
                values[index]++;
            }
        }

        return values;
    }
}
