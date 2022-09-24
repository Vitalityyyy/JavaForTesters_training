package package1;

public class Point {
    public double x;
    public double y;
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }
    public static double distance(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(Math.pow((p1x - p2x), 2) + Math.pow((p1y - p2y), 2));
    }
}
