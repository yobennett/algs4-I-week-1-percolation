public class Percolation {

	private int gridSize;
	private boolean[][] grid;
	private int nodesSize;
	private WeightedQuickUnionUF nodes;
	private int virtualTopNodeIndex;
	private int virtualBottomNodeIndex;

	// constructor
	public Percolation(int gridSize) {

		// gridSize must be positive
		if (gridSize <= 0) {
			throw new IllegalArgumentException("grid size must be positive");
		}

		this.gridSize = gridSize;
		initializeGrid(gridSize);
	}

	private void initializeGrid(int gridSize) {
		// initialize grid, ignore first row and column
		this.grid = new boolean[gridSize + 1][gridSize + 1];

		// initialize nodes for weighted quick-union, union-find
		this.nodesSize = (int) Math.pow(gridSize, 2) + 2;
		this.virtualTopNodeIndex = 0;
		this.virtualBottomNodeIndex = nodesSize - 1;
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

		// not already open
		if (!isOpen(i, j)) {

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
				builder.append(nodes.find(xyTo1D(i, j)));
				builder.append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		System.out.println(p);
	}

}