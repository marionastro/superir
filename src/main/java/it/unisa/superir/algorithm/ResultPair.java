package it.unisa.superir.algorithm;

public class ResultPair {
    private final double titleScore;
    private final double bodyScore;

    public ResultPair(double titleScore, double bodyScore) {
        this.titleScore = titleScore;
        this.bodyScore = bodyScore;
    }

    public double getTitleScore() {
        return titleScore;
    }

    public double getBodyScore() {
        return bodyScore;
    }

    public double getTotalScore() {
        return titleScore + bodyScore;
    }
}
