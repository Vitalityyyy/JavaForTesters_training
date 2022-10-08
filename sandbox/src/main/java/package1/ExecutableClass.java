package package1;

public class ExecutableClass {
    public static void main(String[] args){
        Point p1 = new Point(3,5);
        Point p2 = new Point(8,7);

        System.out.println("Distance between point " + p1 + " and " + p2 + " is " + Point.distance(p1, p2));
    }
}
