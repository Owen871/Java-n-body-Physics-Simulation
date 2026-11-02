/**
 * Owen Gagnon 9/26/25
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;



public class KeyEventsTest {
// test normal keys using a KeyEvent object with null/0 values
// except for the last two which should be the character typed/pressed

Body b1 = new Body("A", new Color(1), 3.0, new Posn(1,2), State.IDLE);
Body b2 = new Body("B", new Color(2), 2.0, new Posn(0,0), State.ACTIVE);
Body b3 = new Body("C", new Color(1), 5.0, new Posn(3,7), State.IDLE);

SimWorld sw = new SimWorld(25000, 3e11, new Posn(3e11, 3e11), b1, b2, b3);
SimWorld sw2 = new SimWorld(25000, 3.796875e11, new Posn(2500, -1000), b3, b2, b1);

@Test
public void testTyped(){
    MenuDisplay menu = new MenuDisplay();
//assertEquals(Main.instance.forceExit(), 
 //            menu.keyTyped(new KeyEvent(null, 0, 0, 0, IWorld.ESCAPE, IWorld.ESCAPE)));

assertEquals(new SimWorld(),
             menu.keyTyped(new KeyEvent(null, 0, 0, 0, '\n', '\n')));

SimWorld sw = new SimWorld();
double oldDt = sw.dt;
sw.keyTyped(new KeyEvent(null, 0, 0, 0, 's', 's'));
assertEquals(oldDt / 1.125, sw.dt);
sw.keyTyped(new KeyEvent(null, 0, 0, 0, 'f', 'f'));
assertEquals(1.0, sw.dt, .01);


PauseDisplay pd = new PauseDisplay(sw);

assertEquals(new PauseDisplay(sw), 
            pd.keyTyped(new KeyEvent(null, 0, 0, 0, 'p', 'p')));

 oldDt = sw.dt;
assertEquals(new SimWorld( oldDt/1.125, sw.b1, sw.b2, sw.b3,), 
            sw.keyTyped(new KeyEvent(null, 0, 0, 0, 's', 's')));

 oldDt = sw.dt;
assertEquals(new SimWorld(sw.b1, sw.b2, sw.b3, (oldDt * 1.125)), 
            sw.keyTyped(new KeyEvent(null, 0, 0, 0, 'f', 'f')));

assertEquals(sw, 
            new PauseDisplay((sw)).keyTyped(new KeyEvent(null, 0, 0, 0, '\n', '\n')));

//assertEquals(Main.instance.forceExit(), 
 //           new PauseDisplay((sw)).keyTyped(new KeyEvent(null, 0, 0, 0, 'x', 'x')));

// test normal keys using a KeyEvent object with null/0 values
// except for the last two which should be the character typed/pressed
assertEquals(new PauseDisplay(sw), 
             sw.keyTyped(new KeyEvent(null, 0, 0, 0, 'p', 'p')));
// arrow keys are "coded" with special key codes
assertEquals(new SimWorld(sw.b1, sw.b2, sw.b3, 1, 3e11, new Posn(3e11, 27e10)), 
             sw.keyPressed(new KeyEvent(null, 0, 0, 0, (char)PApplet.CODED, PApplet.DOWN)));

}

@Override
public String toString() {
    return "KeyEventsTest []";
}




}
