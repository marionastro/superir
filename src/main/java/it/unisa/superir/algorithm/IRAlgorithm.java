package it.unisa.superir.algorithm;

import it.unisa.superir.model.Vocabulary;

@FunctionalInterface
public interface IRAlgorithm {
    default double[] getValues(String string, Vocabulary vocabulary) {
        double[] values = new double[vocabulary.getLength()];
        String[] tokens = string.split("\\W+");

        for (String token : tokens) {
            int index = vocabulary.getIndex(token);

            if (index > 0) {
                values[index]++;
            }
        }

        for (int i = 0; i < values.length; i++) {
            String token = vocabulary.getToken(i);
            values[i] = (values[i] / tokens.length) * getWeight(token);
        }

        return values;
    }

    double getWeight(String term);
}
