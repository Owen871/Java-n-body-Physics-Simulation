/*
 * Owen Gagnon, 10/9/2025
 */

import java.util.Objects;

/**
 * Represents a graphics transformation that can be apply to (x, y) coordinates
 */
public class ScaleTransform implements ITransform {
    double factor;

    

    public ScaleTransform(double factor) {
        this.factor = factor;
    }

    /**
     * Produce the result of multiplying the factor by the Posn
     */
    public Posn apply(Posn p){
        return new Posn(p.getX() * factor, p.getY() * factor);
    }

    /**
     * Produce the inverse of ScaleTransform
     */
    public ITransform inverse(){
        return new ScaleTransform(1.0/factor); 
    }





    // Auto Gen Code
    @Override
    public int hashCode() {
        return Objects.hash(factor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ScaleTransform))
            return false;
        ScaleTransform other = (ScaleTransform) obj;
        return Double.doubleToLongBits(factor) == Double.doubleToLongBits(other.factor);
    }

    @Override
    public String toString() {
        return "ScaleTransform [factor=" + factor + "]";
    }


    

}