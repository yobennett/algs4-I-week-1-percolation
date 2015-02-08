public class PercolationStats {

	private int gridSize;
	private int numExperiments;
	private double[] experimentResults;

	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int n, int t) {
		if (n <= 0) {
			throw new IllegalArgumentException("must be positive number for grid size");
		}

		if (t <= 0) {
			throw new IllegalArgumentException("must be positive number of experiments to run");
		}

		this.gridSize = n;
		this.numExperiments = t;
		this.experimentResults = new double[numExperiments];
		runExperiments();
	}

	private void runExperiments() {
		int i = 0;
		while (i < numExperiments) {
			experimentResults[i] = runExperiment();
			i++;
		}
	}

	private double runExperiment() {
		Percolation percolation = new Percolation(gridSize);
		int p, q, openedSites = 0;
		while (!percolation.percolates()) {
			p = StdRandom.uniform(1, gridSize + 1);
			q = StdRandom.uniform(1, gridSize + 1);
			if (!percolation.isOpen(p, q)) {
				percolation.open(p, q);
				openedSites++;
			}
		}
		return openedSites / Math.pow(gridSize, 2);
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(experimentResults);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return 0.0;
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return 0.0;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return 0.0;
	}

	// test client (described below)
	public static void main(String[] args) {
		if (args.length != 2) {
			// TODO add usage message here
			System.exit(1);
		}
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, t);

		System.out.println("mean = " + stats.mean());
		System.out.println("stddev = " + stats.stddev());
		System.out.println("95% confidence interval = " +  stats.confidenceLo() + ", " + stats.confidenceHi());
		System.out.println("\n");
	}

}
