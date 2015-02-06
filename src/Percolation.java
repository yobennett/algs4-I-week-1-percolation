public class Percolation {

	private int gridSize;
	private boolean[][] grid;

	// constructor
	public Percolation(int gridSize) {
		this.gridSize = gridSize;

		// initialize grid
		this.grid = new boolean[gridSize][gridSize];
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return false;
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return false;
	}

	// does the system percolate?
	public boolean percolates() {
		return false;
	}

	// test client (optional)
	public static void main(String[] args) {}

}