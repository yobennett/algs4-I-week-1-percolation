public class Percolation {

	private int N;
	private boolean[][] grid;
	private WeightedQuickUnionUF sites;

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("grid size must be positive");
		}

		this.N = N;
		initializeGrid();
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		checkWithinGridBounds(i, j);
		if (!isOpen(i, j)) {
			grid[i][j] = true;
			connectToNeighbors(i, j);
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		checkWithinGridBounds(i, j);
		return grid[i][j];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		checkWithinGridBounds(i, j);
		return isOpen(i, j) && sites.connected(0, xyToIndex(i, j));
	}

	// does the system percolate?
	public boolean percolates() {
		for (int i = 1; i <= N; i++) {
			if (isFull(N, i)) {
				return true;
			}
		}
		return false;
	}

	// initialize grid, ignore first row and column
	private void initializeGrid() {
		this.grid = new boolean[N + 1][N + 1];
		int numSites = (N * N) + 2;
		int virtualTopSiteIndex = 0;
		int virtualBottomSiteIndex = numSites - 1;
		this.sites = new WeightedQuickUnionUF(numSites);

		// connect top row to virtual node
		for (int i = 1; i <= N; i++) {
			sites.union(virtualTopSiteIndex, xyToIndex(1, i));
		}

		// connect bottom row to virtual node
		for (int j = 1; j <= N; j++) {
			sites.union(virtualBottomSiteIndex, xyToIndex(N, j));
		}
	}

	private void checkWithinGridBounds(int i, int j) {
		if (!isWithinGridBounds(i, j)) {
			throw new IndexOutOfBoundsException("grid coordinates not within accepted range (i=" + i + ", j=" + j + "+)");
		}
	}

	private boolean isWithinGridBounds(int i, int j) {
		return i > 0 && i <= N && j > 0 && j <= N;
	}

	private int xyToIndex(int x, int y) {
		return ((x - 1) * N) + y;
	}

	private void connectToNeighbors(int i, int j) {
		int[][] neighbors = { {i, j - 1}, {i, j + 1}, {i + 1, j}, {i - 1, j} };
		int x, y;
		for (int[] neighbor : neighbors) {
			x = neighbor[0];
			y = neighbor[1];
			if (x > 0 && x <= N && y > 0 && y <= N && isOpen(x, y)) {
				sites.union(xyToIndex(i, j), xyToIndex(x, y));
			}
		}
	}

}