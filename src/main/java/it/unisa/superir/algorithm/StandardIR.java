package it.unisa.superir.algorithm;

public class StandardIR implements IRAlgorithm {
    @Override
    public double getWeight(String term) {
        return 1.0d;
    }
}
