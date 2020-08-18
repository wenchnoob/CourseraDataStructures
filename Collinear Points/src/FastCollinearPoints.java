import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class FastCollinearPoints {
    private final LineSegment[] segmentsArr;
    
    public FastCollinearPoints(Point[] points) {
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
        return Arrays.copyOf(segmentsArr, numberOfSegments());
    }
    
    private LineSegment[] findSegments(Point[] points) {
        Arrays.sort(points);
        
        ArrayList<LineSegment> segments = new ArrayList<>();
        ArrayList<Point[]> lines = new ArrayList<>();
        
        
        
        
        for (int i = 0; i < points.length; i++) {
            ArrayList<ArrayList<Point>> collinearPoints = findAllCollinear(i, points);
            lines.addAll(makeLines(collinearPoints));
        }
        
        lines = removeDuplicates(lines);
        
        for (Point[] line: lines) {
            segments.add(new LineSegment(line[0], line[1]));
        }
        
        
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    private ArrayList<Point[]> removeDuplicates(ArrayList<Point[]> lines) {
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                if (lines.get(i)[0].compareTo(lines.get(j)[0]) == 0 || 
                        lines.get(i)[1].compareTo(lines.get(j)[1]) == 0)
                    if (lines.get(i)[0].slopeTo(lines.get(i)[1]) == 
                            lines.get(j)[0].slopeTo(lines.get(j)[1])) lines.remove(j--);
            }
        }
        return lines;
    }
    
    
    private ArrayList<ArrayList<Point>> findAllCollinear(int i, Point[] points) {
        Point base = points[i];
        
        Hashtable<Double, ArrayList<Point>> table = new Hashtable<Double, ArrayList<Point>>();
        
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy, base.slopeOrder());
        
        
        
        for (int j = 0; j < copy.length; j++) {
            if (table.containsKey(base.slopeTo(copy[j]))) {
                ArrayList<Point> temp =  table.get(base.slopeTo(copy[j]));
                temp.add(copy[j]);
                table.put(base.slopeTo(copy[j]), temp);
            } else {
                ArrayList<Point> temp = new ArrayList<Point>();
                temp.add(base);
                temp.add(copy[j]);
                table.put(base.slopeTo(copy[j]), temp);
            }
        }
        
        
        Enumeration<Double> keys = table.keys();
        ArrayList<ArrayList<Point>> fourCollinear = new ArrayList<ArrayList<Point>>();
       
        while (keys.hasMoreElements()) {
           double key = keys.nextElement();
           if (table.get(key).size() >= 4) fourCollinear.add(table.get(key));
       }
        
        return fourCollinear;
    }
    
    private ArrayList<Point[]> makeLines(ArrayList<ArrayList<Point>> collinearPoints) {
        ArrayList<Point[]> lines = new ArrayList<>();
        
        for (ArrayList<Point> points : collinearPoints) {
            Point[] line = new Point[2];
            line[0] = findSmallest(points);
            line[1] = findLargest(points);
            lines.add(line);
        }
        
        return lines;
    }
    
    
    
    private Point findSmallest(ArrayList<Point> points) {
        if (points.isEmpty()) throw new IllegalArgumentException();
        Point smallest = points.get(0);
        
        for (Point p : points) {
            if (p.compareTo(smallest) < 0) smallest = p;
        }
        
        return smallest;
    }
    
    private Point findLargest(ArrayList<Point> points) {
        if (points.isEmpty()) throw new IllegalArgumentException();
        Point largest = points.get(0);
        
        for (Point p : points) {
            if (p.compareTo(largest) > 0) largest = p;
        }
        
        return largest;
    }

}
