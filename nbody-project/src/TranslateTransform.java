/*
 * Owen Gagnon, 10/9/25
 */

import java.util.Objects;

public class TranslateTransform implements ITransform {
     
    double x;
    double y;


    
    
    public TranslateTransform(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Produce the result of adding this transform to the given posn
     */
    public Posn apply(Posn p){
        return new Posn(p.getX() + x, p.getY() + y);
    }

    /**
     * Produce the inverse transform of this transform
     */
    public ITransform inverse(){
    return new TranslateTransform( -x, -y);
    }




    // Auto Gen Code

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TranslateTransform))
            return false;
        TranslateTransform other = (TranslateTransform) obj;
        return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
                && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
    }

    @Override
    public String toString() {
        return "TranslateTransform [x=" + x + ", y=" + y + "]";
    }


    
}
