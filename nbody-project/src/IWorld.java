/* 
 * Owen Gagnon, 9/26/25.
 */

import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface IWorld extends PConstants {

	/** produce an updated state of this world after one time tick */
	default
	public IWorld update() { return this; }

	/** produce a visual rendering of this world on the given window */
	default 
	public PApplet draw(PApplet w) { return w; }

	/** produce an updated state of this world after any mouse event */
	default
	public IWorld mouseDefault(MouseEvent mev) { return this; }

	/** produce an updated state of this world after a mouse press event */
	default
	public IWorld mousePressed(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after a mouse release event */
	default
	public IWorld mouseReleased(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after a mouse move event */
	default
	public IWorld mouseMoved(MouseEvent mev) { return mouseDefault(mev); }

	/** produce an updated state of this world after a mouse drag event */
	default
	public IWorld mouseDragged(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after a mouse click event */
	default
	public IWorld mouseClicked(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after a mouse wheel event */
	default
	public IWorld mouseWheel(MouseEvent mev) { return mouseDefault(mev); }

	/** produce an updated state of this world after a mouse enter event on the window */
	default
	public IWorld mouseEntered(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after a mouse exit event on the window */
	default
	public IWorld mouseExited(MouseEvent mev) { return mouseDefault(mev); }
	
	/** produce an updated state of this world after any key event */
	default
	public IWorld keyDefault(KeyEvent kev) { return this; }

	/** produce an updated state of this world after a key press event */
	default
	public IWorld keyPressed(KeyEvent kev) { return keyDefault(kev); }

	/** produce an updated state of this world after a key release event */
	default
	public IWorld keyReleased(KeyEvent kev) { return keyDefault(kev); }

	/** produce an updated state of this world after a key type event */
	default
	public IWorld keyTyped(KeyEvent kev) { return keyDefault(kev); }

	/** should the whole program shut down when the escape key is pressed? */
	default
	public boolean exitOnEscape() { return false; }
	
	/** Constant value for the Escape key in key event handlers */
	public static final int ESCAPE = 27;

}