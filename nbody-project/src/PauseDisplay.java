/**
 * Owen Gagnon 9/26/25
 */


import java.util.Objects;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class PauseDisplay implements IWorld {


   IWorld paused;  // the circle world that is suspended

    public PauseDisplay(IWorld paused) {
        this.paused = paused;
    }

    public PApplet draw(PApplet w) {
        paused.draw(w);
        w.fill(0, 0, 255, 100);  // semi-transparent blue
        w.rect(0, 0, w.width, w.height);
        w.fill(255);
        w.textSize(32);
        w.textAlign(PApplet.CENTER, PApplet.CENTER);
        w.text("Paused", w.width / 2, w.height / 2 - 20);
        w.textSize(16);
        w.text("Press Enter to continue or 'x' to exit.", w.width / 2, w.height / 2 + 20);
        return w;
    }

    public IWorld keyTyped(KeyEvent kev) { 
        if (kev.getKey() == '\n') {
            return paused;  // resumes the circle world by returning it as the active world
        } else if (kev.getKey() == 'x') {
            return Main.instance.forceExit();       // shuts down the entire application
        } else if (kev.getKey() == 'p'){
            return this;
        }
            else {
            return paused.keyTyped(kev);
        }
    }

    public IWorld keyPressed (KeyEvent kev){
        return paused.keyPressed(kev);
    }

    public IWorld mousePressed(MouseEvent mev){
        paused.mousePressed(mev);
        return this;
    }

    public IWorld mouseMoved(MouseEvent mev){
        paused.mouseMoved(mev);
        return this;
    }




    //Auto Gen Code
    @Override
    public String toString() {
        return "PauseDisplay [paused=" + paused + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(paused);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PauseDisplay))
            return false;
        PauseDisplay other = (PauseDisplay) obj;
        return Objects.equals(paused, other.paused);
    }

    
}