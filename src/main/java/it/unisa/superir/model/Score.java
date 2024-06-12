package it.unisa.superir.model;

public class Score implements Comparable<Score> {
    private final long titleScore;
    private final long bodyScore;

    public Score(double titleScore, double bodyScore) {
        this.titleScore = Math.round(titleScore);
        this.bodyScore = Math.round(bodyScore);
    }

    public long getTitleScore() {
        return titleScore;
    }

    public long getBodyScore() {
        return bodyScore;
    }

    public long getTotalScore() {
        return titleScore + bodyScore;
    }

    @Override
    public int compareTo(Score o) {
        return Long.compare(this.getTotalScore(), o.getTotalScore());
    }
}
