package it.unisa.superir.math;

/**
 *This class implements the similarity cosine function.
 * 
 * @author Gruppo5
 */
public final class CosineSimilarity {
    private CosineSimilarity() {}

    /**
     *Funtion to run when needing to calculate the similarity cosine between two double arrays.
     * 
     * @param a     first arrays.
     * @param b     second array.
     * @return      the similarity cosine between a and b.
     */
    public static double compute(double[] a, double[] b) {
        double n = 0.0d;
        double d1 = 0.0d;
        double d2 = 0.0d;

        // a and b should have same length...
        for (int i = 0; i < a.length; i++) {
            n += (a[i] * b[i]);
            d1 += Math.pow(a[i], 2);
            d2 += Math.pow(b[i], 2);
        }

        if ((d1 * d2) == 0)
            return 0.0d;

        return n / (Math.sqrt(d1) * Math.sqrt(d2));
    }
}
