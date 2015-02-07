public class Percolation {

	private int gridSize;
	private boolean[][] grid;
	private int nodesSize;
	private int virtualTopIndex;
	private int virtualBottomIndex;

	// constructor
	public Percolation(int gridSize) {

		// gridSize must be positive
		if (gridSize <= 0) {
			throw new IllegalArgumentException("grid size must be positive");
		}

		this.gridSize = gridSize;

		// initialize grid
		this.grid = new boolean[gridSize][gridSize];

		this.nodesSize = (int) Math.pow(gridSize, 2) + 2;
		this.virtualTopIndex = 0;
		this.virtualBottomIndex = nodesSize - 1;
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) throws IndexOutOfBoundsException {
		if (!isWithinGridBounds(i, j)) {
			throw new IndexOutOfBoundsException("grid coordinates not within accepted range (i=" + i + ", j=" + j + "+)");
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		if (!isWithinGridBounds(i, j)) {
			throw new IndexOutOfBoundsException("grid coordinates not within accepted range (i=" + i + ", j=" + j + "+)");
		}
		return grid[i][j];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
		if (!isWithinGridBounds(i, j)) {
			throw new IndexOutOfBoundsException("grid coordinates not within accepted range (i=" + i + ", j=" + j + "+)");
		}
		return false;
	}

	private boolean isWithinGridBounds(int i, int j) {
		return i > 0 && i <= gridSize && j > 0 && j <= gridSize;
	}

	// does the system percolate?
	public boolean percolates() {
		return false;
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		return;
	}

}