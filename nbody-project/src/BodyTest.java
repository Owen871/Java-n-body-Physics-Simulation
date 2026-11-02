/* 
 * Owen Gagnon,  9/24/25.
 */

import org.junit.*;
import static org.junit.Assert.*;

public class BodyTest {

    Body b1 = new Body("A", new Color(1, 0, 0), 3.0, new Posn(1, 2), new Posn(3, 4), State.IDLE);
    Body b2 = new Body("B", new Color(2, 0, 0), 2.0, new Posn(0, 0), new Posn(0, 0), State.ACTIVE);
    Body b3 = new Body("C", new Color(1, 0, 0), 5.0, new Posn(3, 7), new Posn(-4, 2), State.IDLE);
    Body b4 = new Body("D", new Color(1, 0, 0), 2.5, new Posn(2, 2), new Posn(3, 4), State.HOVER);

    @Test
    public void testForceAppliedBy() {
        assertEquals(new Posn(0.0, 0.0), b1.forceAppliedBy(b1));
        assertEquals(new Posn(Body.G * 7.5, 0.0), b1.forceAppliedBy(b4));
        assertEquals(new Posn(- Body.G * 6 / (Math.pow(5, 1.5)), -2 * Body.G * 6 / (Math.pow(5, 1.5))),
                     b1.forceAppliedBy(b2));                     
        assertEquals(b3.forceAppliedBy(b1).scale(-1), b1.forceAppliedBy(b3));
    }

    @Test
    public void testUpdate() {
        // no acceleration, unit time step - velocity unchanged, position updated with velocity
        assertEquals(new Body("A", new Color(1, 0, 0), 3.0, new Posn(4, 6), new Posn(3, 4), State.IDLE), 
                     b1.update(new Posn(0, 0), 1.0));

        // no acceleration, time step is 3 - velocity unchanged, position updated with velocity * 3
        assertEquals(new Body("A", new Color(1, 0, 0), 3.0, new Posn(10, 14), new Posn(3, 4), State.IDLE), 
                     b1.update(new Posn(0, 0), 3.0));

        // acceleration = (7, 4), time step is 1 - velocity updated with acceleration, position updated with new velocity
        assertEquals(new Body("A", new Color(1, 0, 0), 3.0, new Posn(11, 10), new Posn(10, 8), State.IDLE), 
                     b1.update(new Posn(21, 12), 1.0));

        // acceleration = (-7, 4).scale(.5) = (-3.5, 2), everything updated with .5 time step
        assertEquals(new Body("A", new Color(1, 0, 0), 3.0, new Posn(0.75, 5), new Posn(-0.5, 6), State.IDLE), 
                     b1.update(new Posn(-21, 12), .5));

        // acceleration = (7, -4).scale(3) = (21, -12), time step is 3
        assertEquals(new Body("A", new Color(1, 0, 0), 3.0, new Posn(73, -22), new Posn(24, -8), State.IDLE), 
                     b1.update(new Posn(21, -12), 3.0));
    }

    @Test
    public void testGetSetState() {
        assertEquals(b1, b1.setState(State.IDLE));
        assertEquals(State.ACTIVE, b1.setState(State.ACTIVE).getState());
        assertEquals(State.HOVER, b1.setState(State.HOVER).getState());

        assertEquals(State.IDLE, b2.setState(State.IDLE).getState());    // doesn't change
        assertEquals(State.ACTIVE, b2.setState(State.ACTIVE).getState());    // doesn't change
        assertEquals(State.ACTIVE, b2.setState(State.HOVER).getState());    // doesn't change

        assertEquals(State.IDLE, b4.setState(State.IDLE).getState());
        assertEquals(State.ACTIVE, b4.setState(State.ACTIVE).getState());
        assertEquals(State.HOVER, b4.setState(State.HOVER).getState());
    }

}