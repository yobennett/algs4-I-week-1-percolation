public class PercolationStats {

	private static double CONFIDENCE_INTERVAL_MULTIPLE = 1.96;
	private int N;
	private int T;
	private double[] thresholds;

	// perform T independent experiments on an N-by-N grid
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

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double variance = StdStats.var(thresholds);
		return Math.sqrt(variance);
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((CONFIDENCE_INTERVAL_MULTIPLE * stddev())/Math.sqrt(T));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((CONFIDENCE_INTERVAL_MULTIPLE * stddev())/Math.sqrt(T));
	}

	private void runExperiments() {
		int i = 0;
		while (i < T) {
			thresholds[i] = runExperiment();
			i++;
		}
	}

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

	// test client (described below)
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java PercolationStats N T");
			System.out.println("performs T independent computational experiments on an N-by-N grid");
			System.exit(1);
		}
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, t);

		System.out.printf("%-23s %s %f %n", "mean", "=", stats.mean());
		System.out.printf("%-23s %s %f %n", "stddev", "=", stats.stddev());
		System.out.printf("%-23s %s %s %n", "95% confidence interval", "=", stats.confidenceLo() + ", " + stats.confidenceHi());
	}

}
