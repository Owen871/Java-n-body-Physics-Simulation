/* 
 * Owen Gagnon, 9/26/25.
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import processing.core.*;
import processing.event.*;

/**
 * Provides the scaffolding to launch a Processing application
 */
public class Main extends PApplet {

    
    public static void main(String[] args) {
        PApplet.runSketch(new String[] { instance.getClass().getName() }, instance);
    }   

    private IWorld constructInitialWorld() {
        return new MenuDisplay();                               
    }

    /* change 4 occurrences of `Main` below to match the class name if modified */
      
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final Main instance = new Main();     // singleton design pattern

    private void setupFrameClose() {
        JFrame frame = (JFrame)javax.swing.FocusManager.getCurrentManager().getActiveWindow();
        if (frame != null) {
            frame.addWindowListener(new WindowAdapter() { // taken from Processing code
                @Override
                public void windowClosing(WindowEvent e) {
                Main.super.exit(); 
                }
            });
            this.frameCloseSetup = true;
        }
    }

    private Main() {} 

    /* no changes below here */

    public void settings() {
        this.size(WIDTH, HEIGHT);
    }

    public void setup() {
        w = this.constructInitialWorld();
        setupFrameClose();
    }
    
    public void draw() {
        if (!frameCloseSetup) { setupFrameClose(); }
        w = w.update();
        w.draw(this);
    }

    @Override
    public void exit() {
        if (w.exitOnEscape()) { super.exit(); }
        else { /* ignore */ }
    }

    public IWorld forceExit() {
        super.exit();   
        return this.w;  // unreachable
    }
    
    @Override
    public void mousePressed(MouseEvent mev) {
        w = w.mousePressed(mev);
    }
    
    @Override
   public void mouseReleased(MouseEvent mev) {
    	w = w.mouseReleased(mev);
    }

    @Override
   public void mouseMoved(MouseEvent mev) {
    	w = w.mouseMoved(mev);
    }
    
    @Override
    public void mouseDragged(MouseEvent mev) {
    	w = w.mouseDragged(mev);
    }
    
    @Override
    public void mouseClicked(MouseEvent mev) {
    	w = w.mouseClicked(mev);
    }

    @Override
    public void mouseEntered(MouseEvent mev) {
    	w = w.mouseEntered(mev);
    }

    @Override
    public void mouseExited(MouseEvent mev) {
    	w = w.mouseExited(mev);
    }
    
    @Override
    public void mouseWheel(MouseEvent mev) {
    	w = w.mouseWheel(mev);
    }

    @Override
    public void keyPressed(KeyEvent kev) {
        w = w.keyPressed(kev);
    }

    @Override
    public void keyReleased(KeyEvent kev) {
        w = w.keyReleased(kev);
    }
    
    @Override
    public void keyTyped(KeyEvent kev) {
        w = w.keyTyped(kev);
    }

    @Override
    public void cursor(int kind) {
        if (getSurface() != null) {
            super.cursor(kind);
        }
    }

    private IWorld w;
    private boolean frameCloseSetup = false;
}