package package1;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
    @Test
    public void testDistance1() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);
        Assert.assertEquals(Point.distance(p1.x, p1.y, p2.x, p2.y),1.4142135623730951);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(40,101);
        Point p2 = new Point(-33,0);
        Assert.assertEquals(Point.distance(p1.x, p1.y, p2.x, p2.y),124.61942063739504);
    }
}
