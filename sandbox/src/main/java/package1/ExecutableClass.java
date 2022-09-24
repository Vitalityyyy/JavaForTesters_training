package package1;

public class ExecutableClass {
    public static void main(String[] args){
        Point p1 = new Point(3,5);
        Point p2 = new Point(8,7);

        System.out.println("Distance between point p1(" + p1.x + ", " + p1.y + ") and p2(" + p2.x + ", " + p2.y + ") = " + Point.distance(p1.x, p1.y, p2.x, p2.y));
    }
}
