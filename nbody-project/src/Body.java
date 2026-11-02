/*
 * Owen Gagnon 9/18/25
 */

import java.util.Objects;
import processing.core.PApplet;

/** represents something like a planet or a particle in the simulation. */
public class Body {
String name;
Color color;
double mass;
Posn pos;
Posn vel;
State state;
IPath path;
    
public Body(String name, Color color, double mass, Posn pos, Posn velocity, State state) {
    this.name = name;
    this.color = color;
    this.mass = mass;
    this.pos = pos;
    this.vel = velocity;
    this.state = state;
    this.path = new ArrayPath();  
    this.path.addPoint(this.pos);
}

public Body(String name, Color color, double mass, Posn pos, Posn velocity) {
    this.name = name;
    this.color = color;
    this.mass = mass;
    this.pos = pos;
    this.vel = new Posn(0,0);
    this.state = State.IDLE;
    this.path = new ArrayPath();  
    this.path.addPoint(this.pos);
}
public Body(String name, Color color, double mass, Posn space,State state) {
    this.name = name;
    this.color = new Color(255, 255, 255);
    this.mass = 1.0;
    this.pos = space;
    this.state = state;
    this.path = new ArrayPath();  
    this.path.addPoint(this.pos);
}



public static final double G = 6.67e-11; // gravitational constant



/** computes the force vector (Fx, Fy) exerted on this body by the given body (that)*/
public Posn forceAppliedBy(Body that){
    double dx = that.pos.getX() - this.pos.getX();
    double dy = that.pos.getY() - this.pos.getY();
    double r = Math.sqrt( (dx*dx) + (dy*dy));
    double F = (G * this.mass * that.mass)/(r*r);

    
    
    if(this.equals(that)){
        return new Posn (0.0, 0.0);
    }else{
        return new Posn(F* dx/ r, F* dy/r);
    }
}

    /**
     * Updates the state of this body (velocity and position)
     * based on the given force and time step.
     */
    public Body update(Posn force, double dt) {
        Posn acc = force.scale(1.0 / this.mass);
        Posn newVel = this.vel.sum(acc.scale(dt));
        Posn newPos = this.pos.sum(newVel.scale(dt));
        Body updated = new Body(this.name, this.color, this.mass, newPos, newVel, this.state);
        updated.path = this.path;   
        updated.path.addPoint(newPos);

    return updated;
    }




    /** produces a body with updated State based on the given and current state. Note that if the current state is ACTIVE and the given state is HOVER, then the state remains as is.*/
    public Body setState(State state){
        if (this.state.equals(State.ACTIVE) && state.equals(State.HOVER)){
        return new Body(this.name, this.color, this.mass, this.pos, this.vel, State.ACTIVE);
        } else{
        return new Body(this.name, this.color, this.mass, this.pos, this.vel, state);    
        }
        
    }


    /** a getter method (returns the current state) */
    public State getState(){
        return this.state;
}


    /** a getter method (produces the current position)*/
    Posn getPos(){
        return this.pos;
}

    /** Renders (draws an image of) this body on the given application window. */
    public PApplet draw(PApplet w, ITransform t) {    // PApplet is the Processing application window

    w.fill(255);    // set the current fill color for shapes/text to white

    w.noStroke();   // no outline on the shapes

    float x = (float) t.apply(this.pos).getX(); // Processing uses single-precision floating point

    float y = (float) t.apply(this.pos).getY();  //  (`float`) types for coordinates, so we need to 
                                       // **cast** our `double` values from the Posn to `float`

w.fill(this.color.asInt()); // set to `Color`

w.circle(x, y, 10);  // draw a 10-pixel radius circle centered at x, y with the current fill and stroke

w.fill(255);    // set the current fill color for shapes/text back to white

    // draw a red outline `rect`angle around the circle if state is hover/active
    if (this.state == State.ACTIVE || this.state == State.HOVER) {
        w.noFill();
        w.stroke(255, 0, 0);
        w.rect(x - 10, y - 10, 20, 20);
    }

    // draw a label with the name of this body to the bottom right of the circle
    if(AppSettings.getInstance().showLabels()){
    w.textSize(12);
    w.textAlign(PApplet.LEFT, PApplet.BOTTOM);
    w.text(this.name, x + 10, y + 10);
    }
     if (AppSettings.getInstance().getShowPaths()) {
     this.path.draw(w, t);
 }

    // return the updated window object
    return w;
}




 // Auto-Generated Code

    @Override
    public String toString() {
        return "Body [name=" + name + ", color=" + color + ", mass=" + mass + ", pos=" + pos + ", vel=" + vel
                + ", state=" + state + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, mass, pos, vel, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Body))
            return false;
        Body other = (Body) obj;
        return Objects.equals(name, other.name) && Objects.equals(color, other.color)
                && Double.doubleToLongBits(mass) == Double.doubleToLongBits(other.mass)
                && Objects.equals(pos, other.pos) && Objects.equals(vel, other.vel) && state == other.state;
    }






}
