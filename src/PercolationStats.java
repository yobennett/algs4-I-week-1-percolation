/**
 * In a famous scientific problem, researchers are interested in the following question:
 * if sites are independently set to be open with probability p (and therefore blocked
 * with probability 1 − p), what is the probability that the system percolates? When p
 * equals 0, the system does not percolate; when p equals 1, the system percolates.
 * <p/>
 * When N is sufficiently large, there is a threshold value p* such that when p < p*
 * a random N-by-N grid almost never percolates, and when p > p*, a random N-by-N grid
 * almost always percolates. No mathematical solution for determining the percolation
 * threshold p* has yet been derived. This program to estimates p*.
 */
public class PercolationStats {

    private static double CONFIDENCE_INTERVAL_MULTIPLE = 1.96;
    private int N;
    private int T;
    private double[] thresholds;

    /**
     * Performs a number of percolation experiments and calculates statistics on
     * the system.
     *
     * @param N the dimensions of the grid
     * @param T the number of experiments to run
     */
    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("must be positive number for grid size");
        }

        if (T <= 0) {
            throw new IllegalArgumentException("must be positive number of experiments to run");
        }

        this.N = N;
        this.T = T;
        this.thresholds = new double[this.T];
        runExperiments();
    }

    /**
     * Sample mean of the resultant percolation thresholds from the experiments ran.
     *
     * @return sample mean of the percolation thresholds
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Sample standard deviation of the resultant percolation thresholds from
     * the experiments ran.
     *
     * @return sample standard deviation of the percolation thresholds
     */
    public double stddev() {
        double variance = StdStats.var(thresholds);
        return Math.sqrt(variance);
    }

    /**
     * Low endpoint of the 95% confidence interval for percolation thresholds from
     * the experiments ran.
     *
     * @return low endpoint of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_INTERVAL_MULTIPLE * stddev()) / Math.sqrt(T));
    }

    /**
     * High endpoint of the 95% confidence interval for percolation thresholds from
     * the experiments ran.
     *
     * @return high endpoint of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_INTERVAL_MULTIPLE * stddev()) / Math.sqrt(T));
    }

    /**
     * Runs a set of percolation experiments.
     */
    private void runExperiments() {
        int i = 0;
        while (i < T) {
            thresholds[i] = runExperiment();
            i++;
        }
    }

    /**
     * Runs a percolation experiment.
     *
     * @return the estimated threshold when the system percolates.
     */
    private double runExperiment() {
        Percolation percolation = new Percolation(N);
        int p, q, openedSites = 0;
        while (!percolation.percolates()) {
            p = StdRandom.uniform(1, N + 1);
            q = StdRandom.uniform(1, N + 1);
            if (!percolation.isOpen(p, q)) {
                percolation.open(p, q);
                openedSites++;
            }
        }
        return openedSites / (double) (N * N);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        System.out.printf("%-23s %s %f %n", "mean", "=", stats.mean());
        System.out.printf("%-23s %s %f %n", "stddev", "=", stats.stddev());
        System.out.printf("%-23s %s %s %n", "95% confidence interval", "=", stats.confidenceLo() + ", " + stats.confidenceHi());
    }

}
