package it.unisa.superir.model;

/**
 * This class represents the generic score of a document.
 * @author Gruppo5
 */
public class Score implements Comparable<Score> {
    private final long titleScore;
    private final long bodyScore;

    /**
     * Constructor given the score of the document title score and the document body score.
     * @param titleScore    the score to assign to the document title.
     * @param bodyScore     the score to assign to the document body.
     */
    public Score(double titleScore, double bodyScore) {
        this.titleScore = Math.round(titleScore);
        this.bodyScore = Math.round(bodyScore);
    }

    /**
     * Function to run to get the title score of the document.
     * @return  the score of the title.
     */
    public long getTitleScore() {
        return titleScore;
    }

    /**
     * Function to run to get the body score of the document.
     * @return  the score of the body.
     */
    public long getBodyScore() {
        return bodyScore;
    }

    /**
     * Function to run to get the total score of the document (sum of the score of the title and the score of the body).
     * @return  the score of the title plus the score of the body.
     */
    public long getTotalScore() {
        return titleScore + bodyScore;
    }

    @Override
    public int compareTo(Score o) {
        return Long.compare(this.getTotalScore(), o.getTotalScore());
    }
}
