/*
* Owen Gagnon 9/11/2025
*/



import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import processing.core.PApplet;
import java.io.FileNotFoundException;

import processing.event.*;

public class SimWorld implements IWorld {
    double dt;
    double radius;
    Posn center;
    private Body [] bs;  // an array of body objects
    String filePath;
    
    // dummy constructor]
    public SimWorld() {
        /*   this.bs = new Body[3]; // constructs an array of 3 Body objects
        
        this.bs[0] = new Body("planet", new Color(0, 128, 255), 5.974e8, new Posn(350, 300), new Posn(.05, 0), State.HOVER);
        this.bs[1] = new Body("star-1", new Color(255, 255, 0), 1.989e14, new Posn(300, 75), new Posn(3, 0), State.IDLE);
        this.bs[2] = new Body("star-2", new Color(255, 255, 0), 1.989e14, new Posn(300, 525), new Posn(-3, 0), State.IDLE);*/
        this.bs = new Body[]{
            new Body("planet", new Color(0, 128, 255), 5.974e8, new Posn(350, 300), new Posn(.05, 0), State.HOVER),
            new Body("star-1", new Color(255, 255, 0), 1.989e14, new Posn(300, 75), new Posn(3, 0), State.IDLE),
            new Body("star-2", new Color(255, 255, 0), 1.989e14, new Posn(300, 525), new Posn(-3, 0), State.IDLE)
        };
        
        this.dt = 1.0;
        this.radius = 350.0;
        this.center = new Posn(this.radius,this.radius);
    }

    public SimWorld(double dt, double radius, Posn center, Body ...bs) {
        this.bs = bs;
        this.dt = dt;
        this.radius = radius;
        this.center = center;
    }

    public SimWorld(double dt) {
        this.bs[0] = new Body("planet", new Color(0, 128, 255), 5.974e8, new Posn(350, 300), new Posn(.05, 0), State.HOVER);
        this.bs[1] = new Body("star-1", new Color(255, 255, 0), 1.989e14, new Posn(300, 75), new Posn(3, 0), State.IDLE);
        this.bs[2] = new Body("star-2", new Color(255, 255, 0), 1.989e14, new Posn(300, 525), new Posn(-3, 0), State.IDLE);
        this.dt = dt;
        this.radius = 350.0;
        this.center = new Posn(this.radius,this.radius);
    }

    public SimWorld(String filePath) {
        this.filePath = filePath;
        this.center = new Posn(0, 0);   
        try {
            Scanner sc = new Scanner(new File(this.filePath));
            int numberOfBodies = sc.nextInt();
            this.radius = sc.nextDouble();
            this.dt = sc.nextDouble();  
            this.bs = new Body[numberOfBodies];
            
            for (int i = 0; i < numberOfBodies; i++) {
                
                double px = sc.nextDouble();
                double py = sc.nextDouble();
                double vx = sc.nextDouble();
                double vy = sc.nextDouble();
                double mass = sc.nextDouble();
                
                String name = sc.next();          
                int r = sc.nextInt();             
                int g = sc.nextInt();             
                int b = sc.nextInt();             
                Color color = new Color(r, g, b);
                                
                this.bs[i] = new Body(
                    name,
                    color,
                    mass,
                    new Posn(px, py),
                    new Posn(vx, vy),
                    State.IDLE
                );
            }
            
            sc.close();
        } catch (FileNotFoundException e) {
            constructError("File not found: " + filePath);
        } catch (InputMismatchException e) {
            constructError("Input format error in file: " + filePath);
        } catch (Exception e) {
            constructError("Error reading file: " + filePath + "(" + e.getMessage() + ")");
        }
    } 
    
    
    
    private void constructError(String msg) {
        this.radius = 0;
        this.bs = new Body[0];
        javax.swing.JOptionPane.showMessageDialog(null, msg);
    }
    
    
    /*
    * For all bodies whose current state matches the given one,
    * set their state to IDLE.
    */
    void clearStates(State match){
        for (int i = 0; i < this.bs.length; i++) {
            if (this.bs[i].getState() == match) {
                this.bs[i] = this.bs[i].setState(State.IDLE);
            }
        }
    }
    
    
    /*
    * Produces the index of a Body in the array of bodies whose position
    * is within 10 pixels (*screen coordinates*) of the given mouse event
    * `(mev.getX(), mev.getY())`.
    * Returns -1 if there is no body close to the location of the given
    * mouse event.
    */
    int indexOfBodyAtMouse(MouseEvent mev){
        
        double mouseX = mev.getX();
        double mouseY = mev.getY();
        
        for(int i = 0; i< this.bs.length; i++){
            Posn wp = this.bs[i].getPos();
            
            if (this.getTransform().apply(wp).distanceTo(new Posn(mouseX, mouseY)) <= 10)
                {
                return i;
            }
        }
        return -1;
    }
    
    /**
    * reurns the index of the current ACTIVE body or -1 if none
    */
    
    int indexOfActiveBody() {
        for (int i = 0; i < this.bs.length; i++) {
            if (this.bs[i].getState() == State.ACTIVE) return i;
        }
        return -1;
    }
    
    /**
    * If it clicks on a bosy, that body becomes ACTIVE and every other 
    * body becomes IDLE. The view then centers on the active body.
    * If no body was clicked then the active body is now IDLE.
    */
    
    public IWorld mousePressed(MouseEvent mev){
        int idx = this.indexOfBodyAtMouse(mev);
        
        if(idx != -1){
            this.clearStates(State.ACTIVE);
            this.clearStates(State.HOVER);
            this.bs[idx] = this.bs[idx].setState(State.ACTIVE);
            
            this.center = this.bs[idx].getPos();
        }else{
            this.clearStates(State.ACTIVE);
            this.clearStates(State.HOVER);
        }
        return this;
    }
    
    /**
    * If a body is Active then keep the view centered on it , otherwise
    * set the body under the moues to Hover and others to IDLE.
    */
    @Override
    public IWorld mouseMoved(MouseEvent mev) {
        /*int activeIdx = this.indexOfActiveBody();
        if (activeIdx != -1) {
        System.out.println(activeIdx);
        this.center = new Posn(this.bs[activeIdx].getPos().getX(),
        this.bs[activeIdx].getPos().getY());
        return this;
        }*/
        
        int idx = this.indexOfBodyAtMouse(mev);
        this.clearStates(State.HOVER);
        if (idx != -1) {
            this.bs[idx] = this.bs[idx].setState(State.HOVER);
        }
        
        return this;
        
    }
    
    
    
    /**
    * represents the min/max range of x
    */
    
    public Range xRange(){
        Range r = new Range();
        for (int i =0; i< bs.length; i= i + 1){
            r = r.addValue(bs[i].getPos().getX());
        }
        return r;
        /*return new Range(Math.min(bs[0].pos.getX(), Math.min(bs[1].pos.getX(), bs[2].pos.getX())),
        Math.max(bs[0].pos.getX(), Math.max(bs[1].pos.getX(), bs[2].pos.getX())));*/
    }
    
    /**
    * represents the min/max range of y
    */
    
    public Range yRange(){
        Range r = new Range();
        for (int i =0; i< bs.length; i= i + 1){
            r = r.addValue(bs[i].getPos().getY());
        }
        return r;
        /* return new Range(Math.min(bs[0].pos.getY(), Math.min(bs[1].pos.getY(), bs[2].pos.getY())),
        Math.max(bs[0].pos.getY(), Math.max(bs[1].pos.getY(), bs[2].pos.getY())));*/
    }
    
    /**
    * Arrow keys view on the center point
    
    public void leftArrow(){
    this.center = new Posn(radius + 10, radius);
    }
    
    public void rightArrow(){
    this.center = new Posn(radius - 10, radius);
    } 
    
    public void upArrow(){
    this.center = new Posn(radius - 10, radius);
    }
    
    public void downArrow(){
    this.center = new Posn(radius + 10, radius);
    }*/
    
    
    /**
    * Resets the view so all the bodies are visible on screen
    */
    public void resetView(){
        double newradius = Math.max(xRange().getSpan(), yRange().getSpan());
        Posn newcenter = new Posn(xRange().min + xRange().getSpan()/2.0, yRange().min + yRange().getSpan()/2.0 );     
        this.radius = newradius;
        this.center = newcenter;
        
    }
    
    
    public PApplet draw(PApplet w) { 
        ITransform t = getTransform();
        w.fill(255);
        w.textSize(16);
        w.textAlign(LEFT, BOTTOM);
        w.text("Time step: " + String.format("%.3e", dt), 10, 40);
        w.text("View radius: " + String.format("%.3e", radius), 10, 60);
        w.text("Center: " + this.center.toString(), 10, 80);
        if (AppSettings.getInstance().getShowPaths()) {
            w.text("Max path length: " + AppSettings.getInstance().getMaxPathLength(), 10, 100);
        }  
        w.text("Solar System\n Simulation (" + this.filePath + ")", 10, 20);
        
        
        w.background(0);
         if (AppSettings.getInstance().showHelp) {
    w.text("""
        Controls:
        '.' - toggle paths
        't' - toggle labels
        'r' - reload file
        's' - slow down (decrease time step)
        'f' - speed up (increase time step)
        ' ' - reset view (fit to all bodies)
        '+' or '=' - zoom in
        '-' - zoom out
        '>' - increase max path length
        '<' - decrease max path length
        <arrow keys> - pan view
        'p' - pause simulation
        'h' or '?' - show/hide help
        'ESC' - exit simulation
        """,
        10,
        w.height - 220
    );
} else {
    w.text("'h' or '?' - show/hide help", 10, w.height - 20);
}
        for (int i =0; i< bs.length; i= i + 1) {
            bs[i].draw(w,t);
            
        }
       
        return w;
        
        
        
        /*  w.background(0);
        
        ITransform screen = 
        new ComposeTransform(
        new ComposeTransform(
        new TranslateTransform(center.getX(), center.getY()).inverse(),
        new ScaleTransform(Main.WIDTH/2.0/radius)),
        new TranslateTransform(Main.WIDTH/2.0, Main.HEIGHT/2.0));
        bs[0].draw(w, screen);
        bs[1].draw(w, screen);
        bs[2].draw(w, screen);
        return w;*/
    }
    
    
    private ITransform getTransform() {
        ITransform screen = 
        new ComposeTransform(
            new ComposeTransform(
                new TranslateTransform(center.getX(), center.getY()).inverse(),
                new ScaleTransform(Main.WIDTH/2.0/radius)),
                new TranslateTransform(Main.WIDTH/2.0, Main.HEIGHT/2.0));
                return screen;
            }
            
            
            
            public IWorld keyPressed(KeyEvent kev) { 
                if(kev.getKey() == CODED ){
                    if ( kev.getKeyCode() == DOWN) {
                        this.center = this.center.sum(new Posn(0, this.radius*.10));
                    }else if( kev.getKeyCode() == UP){
                        this.center = this.center.sum(new Posn(0, -this.radius*.10));
                    }else if( kev.getKeyCode() == LEFT){
                        this.center = this.center.sum(new Posn(-this.radius*.10, 0 ));
                    }else if( kev.getKeyCode() == RIGHT){
                        this.center = this.center.sum(new Posn(this.radius*.10, 0));
                    }
                }
                return this;
                
            }
            
            public IWorld keyTyped(KeyEvent kev) { 
                if (kev.getKey() == IWorld.ESCAPE) {
                    return new MenuDisplay();
                }else if (kev.getKey() == 'p') {
                    return new PauseDisplay(this);
                }else if (kev.getKey() == 's'){
                    this.dt = this.dt / 1.125;
                }else if (kev.getKey() == 'f'){
                    this.dt = this.dt * 1.125;
                }else if(kev.getKey() == ' '){
                    this.resetView();
                }else if(kev.getKey() == '-') {
                    this.radius = this.radius * 1.125;
                }else if(kev.getKey() == '+'|| kev.getKey() == '=') {
                    this.radius = this.radius / 1.125;
                }else if(kev.getKey() == 't'){
                    AppSettings.getInstance().setShowLabels(!AppSettings.getInstance().showLabels());
                }else if(kev.getKey() == '.'){
                    AppSettings.getInstance().setShowPaths(!AppSettings.getInstance().getShowPaths());
                }else if(kev.getKey() == '>'){
                    AppSettings.getInstance().setMaxPathLength(AppSettings.getInstance().getMaxPathLength()+ 50);
                }else if(kev.getKey() == '<'){
                if(AppSettings.getInstance().getMaxPathLength()>= 50){
                        AppSettings.getInstance().setMaxPathLength(AppSettings.getInstance().getMaxPathLength() - 50);
                    }else{
                        AppSettings.getInstance().setMaxPathLength(AppSettings.getInstance().getMaxPathLength() - AppSettings.getInstance().getMaxPathLength());
                    }
                }else if(kev.getKey() == 'h' || kev.getKey() == '?'){
                        AppSettings.getInstance().showHelp = !AppSettings.getInstance().showHelp;}
                return this;
            }

            /** updates the world */            
            public IWorld update() { 
                Posn [] fs = new Posn[bs.length];
                
                for (int i =0; i< bs.length; i= i + 1) {
                    fs[i] = new Posn();
                    for (int j =0; j< bs.length; j++) {
                        
                        fs[i] = fs[i].sum(bs[i].forceAppliedBy(bs[j]));
                    }
                    fs[i] = fs[i].sum(bs[i].forceAppliedBy(bs[i]));
                }
                
                for (int i =0; i< bs.length; i= i + 1) {
                    this.bs[i] = this.bs[i].update(fs[i], this.dt);
                    
                    if (this.bs[i].getState() == State.ACTIVE) {
                        this.center = new Posn(
                            this.bs[i].getPos().getX(),
                            this.bs[i].getPos().getY()
                        );
                        
                    }
                }
                return this;
            }
            
            
            
            
            
            
            
            // Auto Gen Code
            
            
            @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + Arrays.hashCode(bs);
                result = prime * result + Objects.hash(dt, radius, center);
                return result;
            }
            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (!(obj instanceof SimWorld))
                    return false;
                SimWorld other = (SimWorld) obj;
                return Double.doubleToLongBits(dt) == Double.doubleToLongBits(other.dt)
                && Double.doubleToLongBits(radius) == Double.doubleToLongBits(other.radius)
                && Objects.equals(center, other.center) && Arrays.equals(bs, other.bs);
            }
            @Override
            public String toString() {
                return "SimWorld [dt=" + dt + ", radius=" + radius + ", center=" + center + ", bs=" + Arrays.toString(bs)
                + "]";
            }
            
            
            
            
            
        }