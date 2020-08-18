//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] pThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        pThresholds = new double[trials];

        Percolation p;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            pThresholds[i] = p.numberOfOpenSites()/ Math.pow(n, 2);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return  StdStats.stddev(pThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();

        return mean - (CONFIDENCE_95 * stddev/ Math.sqrt(pThresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();

        return mean + (CONFIDENCE_95 * stddev/ Math.sqrt(pThresholds.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(StdIn.readInt(), StdIn.readInt());
        StdOut.println("mean \t\t\t = " + ps.mean());
        StdOut.println("stddev \t\t\t = " + ps.stddev());
        StdOut.println("95% confidence interval  = [" + ps.confidenceLo() + ", " +  ps.confidenceHi() + "]");
        
    }

}