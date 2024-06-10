package it.unisa.superir.model;

public class Score implements Comparable<Score> {
    private final double titleScore;
    private final double bodyScore;

    public Score(double titleScore, double bodyScore) {
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

    @Override
    public int compareTo(Score o) {
        return Double.compare(this.getTotalScore(), o.getTotalScore());
    }
}
