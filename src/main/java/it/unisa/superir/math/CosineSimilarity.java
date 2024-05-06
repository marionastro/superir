package it.unisa.superir.math;

public final class CosineSimilarity {
    private CosineSimilarity() {}

    public static double compute(double[] a, double[] b) {
        double n = 0.0d;
        double d1 = 0.0d;
        double d2 = 0.0d;

        // a and b should have same length...
        for (int i = 0; i < a.length; i++)
            n += (a[i] * b[i]);

        for (double j : a)
            d1 += Math.pow(j, 2);

        for (double j : b)
            d2 += Math.pow(j, 2);

        if ((d1 * d2) == 0)
            return 0.0d;

        return Math.max(0, n / (Math.sqrt(d1) * Math.sqrt(d2)));
    }
}
