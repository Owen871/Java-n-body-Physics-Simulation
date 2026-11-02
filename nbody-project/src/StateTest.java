/* 
 * Nadeem Abdul Hamid, 2025.
 */

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Color;

public class StateTest {
    @Test
    public void testIdleStateTransitions() {
        Body body = new Body("Earth", new Color(1,1,1), 5.97e24, new Posn(0, 0), new Posn(0, 0), State.IDLE);
        assertEquals(State.IDLE, body.setState(State.IDLE).getState());
        assertEquals(State.HOVER, body.setState(State.HOVER).getState());
        assertEquals(State.ACTIVE, body.setState(State.ACTIVE).getState());
    }

    @Test
    public void testHoverStateTransitions() {
        Body body = new Body("Mars", new Color(1,1,1), 6.39e23, new Posn(1, 1), new Posn(0, 0), State.HOVER);
        assertEquals(State.IDLE, body.setState(State.IDLE).getState());
        assertEquals(State.HOVER, body.setState(State.HOVER).getState());
        assertEquals(State.ACTIVE, body.setState(State.ACTIVE).getState());
    }

    @Test
    public void testActiveStateTransitions() {
        Body body = new Body("Venus", new Color(1,1,1), 4.87e24, new Posn(2, 2), new Posn(0, 0), State.ACTIVE);
        assertEquals(State.IDLE, body.setState(State.IDLE).getState());
        assertEquals(State.ACTIVE, body.setState(State.HOVER).getState());
        assertEquals(State.ACTIVE, body.setState(State.ACTIVE).getState());
    }
}
