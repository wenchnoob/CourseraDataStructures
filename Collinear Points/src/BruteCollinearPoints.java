import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segmentsArr;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (points[0] == null) throw new IllegalArgumentException();
        
        for (int i = 0; i < points.length; i++) {
            
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        
        segmentsArr = findSegments(Arrays.copyOf(points, points.length));
    }
    
    public int numberOfSegments() {
        return segmentsArr.length;
    }
    
    public LineSegment[] segments() {
        return Arrays.copyOf(segmentsArr, segmentsArr.length);
    }
    
    private LineSegment[] findSegments(Point[] points) {
        ArrayList<LineSegment> segments = new ArrayList<>();
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                            if (points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {
                                segments.add(makeLine(points[i], points[j], points[k], points[l]));
                            }
                        }
                    }
                }
            }
        }
        
        LineSegment[] segmentsA = new LineSegment[segments.size()];
        
        for (int i = 0; i < segments.size(); i++) {
            segmentsA[i] = segments.get(i);
        }
        
        return segmentsA;
    }
    
    private LineSegment makeLine(Point a, Point b, Point c, Point d) {
        return new LineSegment(findSmallest(a, b, c, d), findLargest(a, b, c, d));
    }
    
    private Point findSmallest(Point a, Point b, Point c, Point d) {
        if (a.compareTo(b) < 0 && a.compareTo(c) < 0 && a.compareTo(d) < 0) return a;
        if (b.compareTo(a) < 0 && b.compareTo(c) < 0 && b.compareTo(d) < 0) return b;
        if (c.compareTo(a) < 0 && c.compareTo(b) < 0 && c.compareTo(d) < 0) return c;
        return d;
    }
    
    private Point findLargest(Point a, Point b, Point c, Point d) {
        if (a.compareTo(b) > 0 && a.compareTo(c) > 0 && a.compareTo(d) > 0) return a;
        if (b.compareTo(a) > 0 && b.compareTo(c) > 0 && b.compareTo(d) > 0) return b;
        if (c.compareTo(a) > 0 && c.compareTo(b) > 0 && c.compareTo(d) > 0) return c;
        return d;
    }
}
