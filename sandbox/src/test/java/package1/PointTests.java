package package1;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
    @Test
    public void testDistanceWithCorrectData() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);
        double distanceActual = Point.distance(p1, p2);
        double distanceExpected = Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2));
        Assert.assertEquals(distanceActual,distanceExpected);
    }

    @Test
    public void testDistanceIsNotNull() {
        Point p1 = new Point(40,101);
        Point p2 = new Point(-33,0);
        Assert.assertNotNull(Point.distance(p1, p2));
    }

    @Test
    public void testDistanceWithIncorrectData() {
        Point p1 = new Point(0.5,8.24);
        Point p2 = new Point(-31.02,0);
        Point p3 = new Point (20,40)
        double distanceActual = Point.distance(p1, p2);
        double distanceIncorrect = Point.distance(p1, p3);
        Assert.assertNotEquals(distanceActual,distanceIncorrect);
    }
}
