public class Percolation {

	private int gridSize;
	private boolean[][] grid;
	private WeightedQuickUnionUF nodes;

	// constructor
	public Percolation(int gridSize) {
		if (gridSize <= 0) {
			throw new IllegalArgumentException("grid size must be positive");
		}

		this.gridSize = gridSize;
		initializeGrid(gridSize);
	}

	// initialize grid, ignore first row and column
	private void initializeGrid(int gridSize) {
		this.grid = new boolean[gridSize + 1][gridSize + 1];

		// initialize nodes for weighted quick-union, union-find
		int nodesSize = (int) Math.pow(gridSize, 2) + 2;
		int virtualTopNodeIndex = 0;
		int virtualBottomNodeIndex = nodesSize - 1;
		this.nodes = new WeightedQuickUnionUF(nodesSize);

		// connect top row to virtual node
		for (int i = 1; i <= gridSize; i++) {
			nodes.union(virtualTopNodeIndex, xyTo1D(1, i));
		}

		// connect bottom row to virtual node
		for (int j = 1; j <= gridSize; j++) {
			nodes.union(virtualBottomNodeIndex, xyTo1D(gridSize, j));
		}
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) throws IndexOutOfBoundsException {
		checkWithinGridBounds(i, j);
		if (!isOpen(i, j)) {
			grid[i][j] = true;
			connectToNeighbors(i, j);
		}
	}

	private void connectToNeighbors(int i, int j) {
		int[][] neighbors = { {i, j - 1}, {i, j + 1}, {i + 1, j}, {i - 1, j} };
		int x, y;
		for (int[] neighbor : neighbors) {
			x = neighbor[0];
			y = neighbor[1];
			if (x > 0 && x <= gridSize && y > 0 && y <= gridSize && isOpen(x, y)) {
				nodes.union(xyTo1D(i,j), xyTo1D(x,y));
			}
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		checkWithinGridBounds(i, j);
		return grid[i][j];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
		checkWithinGridBounds(i, j);
		return isOpen(i, j) && nodes.connected(0, xyTo1D(i, j));
	}

	private void checkWithinGridBounds(int i, int j) {
		if (!isWithinGridBounds(i, j)) {
			throw new IndexOutOfBoundsException("grid coordinates not within accepted range (i=" + i + ", j=" + j + "+)");
		}
	}

	private boolean isWithinGridBounds(int i, int j) {
		return i > 0 && i <= gridSize && j > 0 && j <= gridSize;
	}

	private int xyTo1D(int x, int y) {
		return ((x - 1) * gridSize) + y;
	}

	// does the system percolate?
	public boolean percolates() {
		for (int i = 1; i <= gridSize; i++) {
			if (isFull(gridSize, i)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= gridSize; i++) {
			for (int j = 1; j <= gridSize; j++) {
				if (isOpen(i, j)) {
					builder.append("O");
				} else {
					builder.append("■");
				}
//				builder.append(nodes.find(xyTo1D(i, j)));
//				builder.append(" ");
//				builder.append("full?=" + isFull(i, j));
				builder.append(" ");
			}
			builder.append("\n");
		}
		builder.append("percolates? " + percolates());
		return builder.toString();
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation p = new Percolation(3);

		// open first site
		System.out.println("\nopen (1,1)");
		p.open(1, 1);
		System.out.println(p);

		// open rest of first column
		System.out.println("\nopen (2,1), (3,1)");
		p.open(2, 1);
		p.open(3, 1);
		System.out.println(p);
	}

}