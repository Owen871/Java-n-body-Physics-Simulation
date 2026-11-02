/*
 * Owen Gagnon 9/11/2025
 */



import java.util.Objects;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Represents an interactive application where a drop of
 * water falls down from the top of the window. If the 
 * user clicks the mouse, the drop is moved over to the
 * location of the click;
 */
public class CircleWorld implements IWorld {
	
    // the position of the drop
    double x;
    double y;

    public CircleWorld(double x, double y) {
        this.x = x;
        this.y = y; 
    }
    
    /**
     * Renders a picture of the drop on the window
     */
    public PApplet draw(PApplet c) {
        c.background(255);
        c.fill(0, 0, 255);
        c.circle((int)this.x, (int)this.y, 15);
        return c;
    }

	/**
     * Produces an updated world where the drop moves
     * down a little bit, if it hasn't hit the bottom
     * of the screen yet.
     */
    public CircleWorld update() {
        if (this.y < 400) {
            return new CircleWorld(this.x, this.y + .5);
        } else {
            return this;
        }
    }
    
    /**
     * Produces an updated world with the position of the
     * drop updated to the location of the mouse press.
     */
    public CircleWorld mousePressed(MouseEvent mev) {
        return new CircleWorld(mev.getX(), mev.getY());
    }

    /**
     * Returns to the welcome view if the escape key 
     * is pressed. Exits the application completely 
     * if 'x' is pressed. Pauses the animation if the 
     * enter key is pressed. 
     * Otherwise, it returns the default
     * behavior of the keyTyped method.
     */
    public IWorld keyTyped(KeyEvent kev) { 
        if (kev.getKey() == ESCAPE) {
            return new WelcomeWorld();
        } else if (kev.getKey() == '\n') {
            return new PauseWorld(this);    // wraps the current world in a pause world
                                            // and returns the pause world as the active world
        } else if (kev.getKey() == 'x') {
            return Main.instance.forceExit();
        } else {
            return keyDefault(kev);
        }
    }
    
    /**
     * Produces a string rendering of the position of the
     * drop
     */
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CircleWorld other = (CircleWorld) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}

}