import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

public class PathTest {
    ArrayPath p1 = new ArrayPath();

     @Test
 public void testSamePoints() {
     int savePathLength = AppSettings.getInstance().getMaxPathLength();

     AppSettings.getInstance().setMaxPathLength(3);
     p1.addPoint(new Posn(40, 20));
     p1.addPoint(new Posn(10, 15));
     p1.addPoint(new Posn(90, 40));
     p1.addPoint(new Posn(70, 34));
     assertTrue(p1.samePoints(new Posn[] { new Posn(10, 15), new Posn(90, 40), new Posn(70, 34) } ));

     p1.addPoint(new Posn(50, 12));
     assertTrue(p1.samePoints(new Posn[] { new Posn(90, 40), new Posn(70, 34), new Posn(50, 12) } ));

     AppSettings.getInstance().setMaxPathLength(savePathLength);
 }
}
