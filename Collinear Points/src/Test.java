import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdDraw;

public class Test {
    private static Point[] points;

    public static void main(String[] args) throws FileNotFoundException {
        readInPoints();
        FastCollinearPoints test = new FastCollinearPoints(points);
        prepCanvas();
        drawPoints();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (LineSegment line : test.segments()) {
            line.draw();
        }
        
        System.out.println(test.numberOfSegments());

        System.out.println("done");
    }

    private static void readInPoints() throws FileNotFoundException {
        File input = new File("C:/Users/Student/Desktop/collinear/horizontal50.txt");
        Scanner in = new Scanner(input);
        points = new Point[in.nextInt()];

        int i = 0;
        while (in.hasNext()) {
            points[i] = new Point(in.nextInt(), in.nextInt());
            i++;
        }

        in.close();
    }

    private static void prepCanvas() {
        StdDraw.setCanvasSize(875, 875);
        StdDraw.setXscale(0, 35000);
        StdDraw.setYscale(0, 35000);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
    }

    private static void drawPoints() {
        for (Point p: points) {
            p.draw();
        }
    }

}
