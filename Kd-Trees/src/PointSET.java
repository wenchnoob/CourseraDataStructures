import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    private SET<Point2D> set;
    
    public PointSET() {
        set = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return set.size() == 0;
    }
    
    public int size() {
        return set.size();
    }
    
    public void insert(Point2D p) {
        set.add(p);
    }
    
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    
    public void draw() {
        StdDraw.setPenRadius(0.0125);
        
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            points.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> inRange = new ArrayList<Point2D>();
        
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            Point2D curPoint = points.next();
            if (rect.contains(curPoint)) inRange.add(curPoint);
        }
        
        return inRange;
    }

    public Point2D nearest(Point2D p) {
        double leastDistance = Integer.MAX_VALUE;
        Point2D closest = null;
        
        Iterator<Point2D> points = set.iterator();
        while (points.hasNext()) {
            Point2D curPoint = points.next();
            double curDistance = p.distanceSquaredTo(curPoint);
            
            if (curDistance < leastDistance) {
                leastDistance = curDistance;
                closest = curPoint;
            }
        }
        
        return closest;
    }

    public static void main(String[] args) {
        PointSET points = new PointSET();
        points.insert(new Point2D(0.3, 0.4));
        points.draw();
    }
}