package package1;

public class ExecutableClass {
    public static void main(String[] args){
        Point p1 = new Point(3,5);
        Point p2 = new Point(8,7);

        System.out.println("Distance between point " + p1 + " and " + p2 + " is " + distance(p1, p2));
        System.out.println("Distance between point " + p1 + " and " + p2 + " is " + p1.distance(p2));
    }
    public static double distance(Point p1, Point p2){
        return Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2));
    }
}
