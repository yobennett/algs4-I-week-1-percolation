/**
 * We model a percolation system using an N-by-N grid of sites. Each site is
 * either open or blocked. A full site is an open site that can be connected
 * to an open site in the top row via a chain of neighboring (left, right,
 * up, down) open sites. We say the system percolates if there is a full site
 * in the bottom row. In other words, a system percolates if we fill all open
 * sites connected to the top row and that process fills some open site on
 * the bottom row.
 * <p/>
 * To determine when a system percolates, we use a weighted union-find data
 * structure to represent the sites. Virtual sites are added to the top and
 * bottom of the grid. As a site is opened it is connected to its neighbors.
 * The system percolates once the virtual sites are in the same connected
 * component.
 */
public class Percolation {

    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sitesWithoutVirtualBottomSite;

    /**
     * Constructs a percolation system.
     *
     * @param N the dimensions of the sites grid
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("grid size must be positive");
        }

        this.N = N;
        initializeGrid();
    }

    /**
     * Opens a site and connects the site to its open neighbors.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     */
    public void open(int p, int q) {
        checkWithinGridBounds(p, q);
        if (!isOpen(p, q)) {
            grid[p][q] = true;
            connectToOpenNeighbors(p, q);
        }
    }

    /**
     * Checks if a site is open.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     * @return true if the site is open
     */
    public boolean isOpen(int p, int q) {
        checkWithinGridBounds(p, q);
        return grid[p][q];
    }

    /**
     * Checks if a site is full.
     *
     * To prevent backwash, we check against the sites without
     * a virtual bottom site. Otherwise, we may incorrectly
     * mark a site as full.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     * @return true if the site is full
     */
    public boolean isFull(int p, int q) {
        checkWithinGridBounds(p, q);
        return isOpen(p, q) && sitesWithoutVirtualBottomSite.connected(0, gridToIndex(p, q));
    }

    /**
     * Checks if the system percolates from top to bottom.
     *
     * @return true if the system percolates
     */
    public boolean percolates() {
        if (N == 1) {
            return isOpen(1, 1);
        } else {
            return sites.connected(0, (N * N) + 1);
        }
    }

    /**
     * Initialize the sites grid. We add an row and column to
     * make it easier to follow the 1-based coordinates. We
     * effectively ignore this first row and column.
     */
    private void initializeGrid() {
        this.grid = new boolean[N + 1][N + 1];
        int numSites = (N * N) + 2;
        this.sites = new WeightedQuickUnionUF(numSites);
        this.sitesWithoutVirtualBottomSite = new WeightedQuickUnionUF(numSites - 1);
    }

    /**
     * Checks if the given site coordinates are within the grid and
     * throws an {@link java.lang.IndexOutOfBoundsException} otherwise.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     */
    private void checkWithinGridBounds(int p, int q) {
        if (!isWithinGridBounds(p, q)) {
            throw new IndexOutOfBoundsException("grid coordinates not within accepted range (p=" + p + ", q=" + q + "+)");
        }
    }

    /**
     * Returns true if a given site coordinate is within the grid
     * excluding the first row and column.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     * @return true if a given site coordinate is within the grid
     */
    private boolean isWithinGridBounds(int p, int q) {
        return p > 0 && p <= N && q > 0 && q <= N;
    }

    /**
     * Converts a given site coordinate to an one-dimensional index
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     * @return the converted coordinate as an index
     */
    private int gridToIndex(int p, int q) {
        return ((p - 1) * N) + q;
    }

    /**
     * Connects an open site with its open neighbors.
     *
     * @param p the row coordinate for the target site
     * @param q the column coordinate for the target site
     */
    private void connectToOpenNeighbors(int p, int q) {
        int index = gridToIndex(p, q);

        if (p == 1) {
            // if first row connect to virtual top in both data structures
            sites.union(0, index);
            sitesWithoutVirtualBottomSite.union(0, index);
        } else if (p == N) {
            // if last row connect to virtual bottom in ONLY main data structure
            sites.union((N * N) + 1, index);
        }

        // top
        if (isWithinGridBounds(p - 1, q) && isOpen(p - 1, q)) {
            sites.union(gridToIndex(p - 1, q), index);
            sitesWithoutVirtualBottomSite.union(gridToIndex(p - 1, q), index);
        }

        // bottom
        if (isWithinGridBounds(p + 1, q) && isOpen(p + 1, q)) {
            sites.union(gridToIndex(p + 1, q), index);
            sitesWithoutVirtualBottomSite.union(gridToIndex(p + 1, q), index);
        }

        // left
        if (isWithinGridBounds(p , q - 1) && isOpen(p, q - 1)) {
            sites.union(gridToIndex(p, q - 1), index);
            sitesWithoutVirtualBottomSite.union(gridToIndex(p, q - 1), index);
        }

        // right
        if (isWithinGridBounds(p , q + 1) && isOpen(p, q + 1)) {
            sites.union(gridToIndex(p, q + 1), index);
            sitesWithoutVirtualBottomSite.union(gridToIndex(p, q + 1), index);
        }
    }

}