/*
 * Owen Gagnon 9/11/2025
 */

import java.util.Objects;


/**
 * for class Posn, x and y attributes  represent a 2-dimensional point or vector.
 */

public class Posn {
    private double x;
    private double y;

    public Posn(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public Posn(){
        this(0.0, 0.0);

    }

    /**Produces the distance between this and that posn
     */
    public double distanceTo (Posn that)
    {
        return Math.sqrt(Math.pow((that.x - this.x), 2) + Math.pow((that.y-this.y), 2));
    
    }
    /** Produce a new Posn with each coordinate of this one multiplied by d
     */
    public Posn scale (double d){
        return new Posn(this.x *d, this.y * d);
     
    }

    public Posn sum (Posn that){
     return (new Posn(that.x+this.x, that.y+this.y ));
    }
    /**
     * Subtract the components of this from that
     */
    public Posn diff (Posn that){
        return (new Posn(that.x-this.x, that.y-this.y ));
    }

    /**
     * Getter methods for x and y
     */
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }




    
    @Override
    public String toString() {
        return "Posn [x=" + x + ", y=" + y + ", getX()=" + getX() + ", getY()=" + getY() + ", getClass()=" + getClass()
                + "]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Posn))
            return false;
        Posn other = (Posn) obj;
        double epsilon = 1e-2; // Small relative tolerance
         return Math.abs(x - other.x) <= epsilon * Math.max(Math.abs(x), Math.abs(other.x))
        & Math.abs(y - other.y) <= epsilon * Math.max(Math.abs(y), Math.abs(other.y));
    }


   
   

    



    

}