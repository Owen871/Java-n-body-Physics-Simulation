/*
 * Owen Gagnon, 10/9/25
 */

import java.util.Objects;

public class ComposeTransform implements ITransform{
    ITransform t1;
    ITransform t2; 
    
    
      public ComposeTransform(ITransform t1, ITransform t2) {
        this.t1 = t1;
        this.t2 = t2;
    }


    /**
     * Produce the result of applying this transform to the given posn in order of t1 to t2
     */
    public Posn apply(Posn p){
        return t2.apply(t1.apply(p));
    }

  

    /**
     * Produce the inverse of the composition of inverses
     */
    public ITransform inverse(){
        return new ComposeTransform(t1.inverse(), t2.inverse());
    }




    //Auto Gen Code
    @Override
    public int hashCode() {
        return Objects.hash(t1, t2);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ComposeTransform))
            return false;
        ComposeTransform other = (ComposeTransform) obj;
        return Objects.equals(t1, other.t1) && Objects.equals(t2, other.t2);
    }


    @Override
    public String toString() {
        return "ComposeTransform [t1=" + t1 + ", t2=" + t2 + "]";
    }


    

}
