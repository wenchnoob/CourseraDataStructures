import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid = null;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        grid = new int[n][n];


        uf = new WeightedQuickUnionUF((int) Math.pow(n, 2) + 2);

        for (int i = 0; i < (int) Math.pow(n, 2) + 2; i++) {
            if (i < n) uf.union(0, i + 1);
            if (i > (int) Math.pow(n, 2) - n) uf.union(i, (int) Math.pow(n, 2) + 1);
        }


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (grid[row - 1][col - 1] == 0) {
            grid[row - 1][col - 1] = 1;
            openSites++;

            if (col - 2 >= 0) {
                if (grid[row - 1][col - 2] == 1 || grid[row - 1][col - 2] == 2) {
                    uf.union((grid.length * (row - 1) + col), (grid.length * (row -1) + col - 1));
                }
            }

            if (col < grid.length) {
                if (grid[row - 1][col] == 1 || grid[row - 1][col] == 2) {
                    uf.union((grid.length * (row - 1) + col), (grid.length * (row -1) + col + 1));
                }
            }

            if (row - 2 >= 0) {
                if (grid[row - 2][col - 1] == 1 || grid[row - 2][col - 1] == 2) {
                    uf.union((grid.length * (row - 1) + col), (grid.length * (row - 2) + col));
                }
            }

            if (row <  grid.length) {
                if (grid[row][col - 1] == 1 || grid[row][col - 1] == 2) {
                    uf.union((grid.length * (row - 1) + col), (grid.length * (row) + col));
                }
            }
            
            
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        if (grid[row - 1][col - 1] != 0) return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        
        if(!percolates())
            if (grid[row - 1][col - 1] != 0)
                if (uf.find((grid.length * (row - 1) + col)) == uf.find(0)) return true;
        if(percolates())
            if (grid[row - 1][col - 1] != 0)
                if (uf.find((grid.length * (row - 1) + col)) == uf.find(0) && 
                        uf.find((grid.length * (row - 1) + col)) == uf.find((int) Math.pow(n, 2) + 1)) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites() == 0) return false;
        if (uf.find(0) == uf.find((int) Math.pow(n, 2) - 1)) return true;
        return false;
    }

    private void validate(int row, int col) {
        if (row > n || row < 1) throw new IllegalArgumentException();
        if (col > n || col < 1) throw new IllegalArgumentException();
    }
}