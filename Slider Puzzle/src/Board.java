import java.util.ArrayList;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int size, manhattanC;
    private int hammingC;
    private final int[][] board;
    private Board twin = null;
    
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        
        size = tiles.length;
        board = new int[tiles.length][tiles[0].length];
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = tiles[i][j];
            }
        }
        manhattanC = manhattan();
    }

    
    // string representation of this board
    public String toString() {
        StringBuilder arr = new StringBuilder();
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                arr.append(board[i][j] + " ");
            }
            arr.append("\n");
        }
        
        return size + " \n" + arr;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingC != 0) return hammingC;
        
        int hamming = 0;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) continue;
                if (board[i][j] != (size * i) + j + 1) hamming++;
            }
        }
        
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanC != 0) return manhattanC;
        
        int manhattan = 0;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int n = board[i][j];
                
                int expectedRow;
                if (n % size != 0) expectedRow = n/size + 1;
                else expectedRow = n/size;
                
                int expectedCol;
                if (n % size == 0) expectedCol = size;
                else expectedCol = n % size;
                
                if (n != 0) manhattan += Math.abs(i + 1 - expectedRow) + Math.abs(j + 1 - expectedCol);
                
            }
        }
        
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.manhattanC == 0) return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        if (((Board) y).dimension() != dimension()) return false;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != ((Board) y).board[i][j]) return false;
            }
        }
        
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        
        int row = 0;
        int col = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        
        Board neighbor = leftNeighbor(row, col);
        if (neighbor != null) neighbors.add(neighbor);
        
        neighbor = rightNeighbor(row, col);
        if (neighbor != null) neighbors.add(neighbor);
        
        neighbor = topNeighbor(row, col);
        if (neighbor != null) neighbors.add(neighbor);
        
        neighbor = botNeighbor(row, col);
        if (neighbor != null) neighbors.add(neighbor);
        
        return neighbors;
    }
    
    private Board leftNeighbor(int row, int col) {
        if (col == 0) return null;
        
        int[][] tiles = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tiles[i][j] = board[i][j];
            }
        }
        
        tiles[row][col] = tiles[row][col-1]; 
        tiles[row][col-1] = 0;
        
        return new Board(tiles);
    }
    
    private Board rightNeighbor(int row, int col) {
        if (col == size - 1) return null;
        
        int[][] tiles = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tiles[i][j] = board[i][j];
            }
        }
        
        tiles[row][col] = tiles[row][col+1]; 
        tiles[row][col+1] = 0;
        
        return new Board(tiles);
    }
    
    private Board topNeighbor(int row, int col) {
        if (row == 0) return null;
        
        int[][] tiles = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tiles[i][j] = board[i][j];
            }
        }
        
        tiles[row][col] = tiles[row-1][col]; 
        tiles[row-1][col] = 0;
        
        return new Board(tiles);
    }
    
    private Board botNeighbor(int row, int col) {
        if (row == size - 1) return null;
        
        int[][] tiles = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tiles[i][j] = board[i][j];
            }
        }
        
        tiles[row][col] = tiles[row+1][col]; 
        tiles[row+1][col] = 0;
        
        return new Board(tiles);
    }
    
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin != null) return twin;
        
        int[][] twinA = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                twinA[i][j] = board[i][j];
            }
        }
        
        boolean twinned = false;
        do {
            int row = StdRandom.uniform(dimension());
            int col = StdRandom.uniform(dimension());
            
            int row2 = StdRandom.uniform(dimension());
            int col2 = StdRandom.uniform(dimension());
            
            if (twinA[row][col] != 0 && twinA[row2][col2] != 0)
                if (row != row2 || col != col2) {
                    int temp = twinA[row][col];
                    twinA[row][col] = twinA[row2][col2];
                    twinA[row2][col2] = temp;
                    twinned = true;
                }
            
            
        } while (!twinned);
        
        twin = new Board(twinA);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = {{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};
        int[][] tiles2 = {{1, 0, 2}, {7, 5, 4}, {8, 6, 3}};
        // int[][] tiles3 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        
        Board b1 = new Board(tiles2);
        // Board b2 = new Board(tiles3);
        
        System.out.println(b1);
        // System.out.println(b1.isGoal());
        // System.out.println(b1.equals(b2));
        // System.out.println(b1.twin());
        
        // System.out.println(b1.twin());
        
        b1.neighbors().forEach(System.out::println);
    }

}