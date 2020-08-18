import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private int moves = 0;
    private boolean solveable = true;
    private final ArrayList<Board> ansPath;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Node goal = solution(initial);
        ArrayList<Board> path = new ArrayList<>();
        while (goal != null) {
            path.add(goal.board);
            goal = goal.prev;
        }
        Collections.reverse(path);
        ansPath = path;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (ansPath.isEmpty()) return null;
        return ansPath;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles2 = {{8, 6, 7}, {2, 4, 5},  {1, 3, 0}};
        // int[][] tiles2 = {{1, 0}, {2, 3}};
        // int[][] tiles3 = {{0, 1, 2, 3}, {5, 6, 7, 4}, {9, 10, 11, 8}, {13, 14, 15, 12}};

        Board b1 = new Board(tiles2);

        Solver solver = new Solver(b1);
        solver.solution().forEach(System.out::println);
        System.out.println(solver.moves());
    }

    private final class Node implements Comparable<Node> {
        private final int move, manhattan;
        private final Board board;
        private final Node prev;
        private final boolean isTwin;

        public Node(int move, Board board, Node prev, boolean twin) {
            this.move = move;
            this.board = board;
            this.prev = prev;
            this.manhattan = board.manhattan();
            this.isTwin = twin;
        }

        @Override
        public int compareTo(Node that) {
            // TODO Auto-generated method stub
            if ((this.manhattan + this.move) - (that.manhattan + that.move) != 0) return (this.manhattan + this.move) - (that.manhattan + that.move);

            return this.manhattan - that.manhattan;
        }
    }

    private Node solution(Board board) {
        if (board.isGoal()) return new Node(0, board, null, false);

        boolean solved = false;

        // For main board
        MinPQ<Node> brain = new MinPQ<>();
        brain.insert(new Node(0, board, null, false));
        brain.insert(new Node(0, board.twin(), null, true));

        Node goal = null;
        Node cur = brain.delMin();
        for (Board b: cur.board.neighbors()) {
            brain.insert(new Node(moves+1, b, cur, cur.isTwin));
        }

        Board previous = null;


        while (true) {
            cur = brain.delMin();
            moves = cur.move;
            for (Board b: cur.board.neighbors()) {
                if (!b.equals(previous)) {
                    brain.insert(new Node(moves+1, b, cur, cur.isTwin));
                }
            }

            if (cur.board.isGoal()) {
                if (cur.isTwin){
                    solved = false;
                    break;
                }
                
                goal = cur;
                solved = true;
                break;
            }

            previous = cur.board;
        }

        if (solved) {
            solveable = true;
            return goal;
        } else {
            solveable = false;
            return null;
        }
    }
}