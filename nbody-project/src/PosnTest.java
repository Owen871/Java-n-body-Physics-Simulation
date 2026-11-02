import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PosnTest {

    double DELTA = 1e-6;

    @Test
    public void testConstructorAndGetters() {
        Posn p = new Posn(3.0, 4.0);
        assertEquals(3.0, p.getX(), DELTA);
        assertEquals(4.0, p.getY(), DELTA);

        Posn q = new Posn(3.5, -2.5);
        assertEquals(3.5, q.getX(), DELTA);
        assertEquals(-2.5, q.getY(), DELTA);

        Posn r = new Posn();
        assertEquals(0.0, r.getX(), DELTA);
        assertEquals(0.0, r.getY(), DELTA);
    }

    @Test
    public void testDistanceTo() {
        Posn p1 = new Posn(0, 0);
        Posn p2 = new Posn(3, 4);
        assertEquals(5.0, p1.distanceTo(p2), DELTA);
    }

    @Test
    public void testScale() {
        Posn p = new Posn(2, 3);
        Posn scaled = p.scale(2);
        assertEquals(new Posn(4, 6), scaled);

        Posn q = new Posn(2, -3);
        Posn scaledq = q.scale(-2.5);
        assertEquals(new Posn(-5, 7.5), scaledq);
    }

    @Test
    public void testSum() {
        Posn p1 = new Posn(1, 2);
        Posn p2 = new Posn(3, 4);
        assertEquals(new Posn(4, 6), p1.sum(p2));
        assertEquals(new Posn(0, 0), p1.sum(new Posn(-1, -2)));
    }

    @Test
    public void testDiff() {
        Posn p1 = new Posn(5, 7);
        Posn p2 = new Posn(2, 3);
        Posn p3 = new Posn(10, 5);
        assertEquals(new Posn(-3.0, -4.0), p1.diff(p2));
        assertEquals(new Posn(-5.0, 2.0), p3.diff(p1));
        assertEquals(new Posn(0.0, 0.0), p1.diff(p1));
        

    }
}
