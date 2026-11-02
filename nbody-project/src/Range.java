import java.util.Objects;

/**
 * Owen Gagnon 9/11/2025
 */


/**
 * A class that has double min and max values and it also keeps track if it is initialized or not.
 */
public class Range {
    double min;
    double max;
    boolean initialized;
    public Range( double min, double max){
        this.min = min;
        this.max = max;
        this.initialized = true;
    }
    public Range (){
        this.initialized = false;
    }
    public Range( double v){
        this.min = v;
        this.max = v;
        this.initialized = true;
    }

/**
 * returns the Range with the updated values and initialized turned true
 */
    public Range addValue(double value){

        if ( ! initialized ){ 
            return new Range(value, value);
        } else if( value > this.max) {
            return new Range(this.min, value);
        }
        else if (value < this.min){
            return new Range( value, this.max);
        }else{
            return this;
        }
 
    }

    /**
     *  produces the magnitude (absolute value) of the 
     * difference between the min and max values. Produces 0.0 if not initialized.
     */

    public double getSpan() {
        if(initialized == false){
            return 0.0;
        }
        else {
            return Math.abs(this.max -this.min);}
    }

    /**
     * determines whether the given value falls within the given range, inclusive of min and max values. 
     * If the range is not initialized, this method always returns false
     */
    public boolean contains (double value){
        if( initialized == false){
            return false;
        }
        else{
            return value >= min && value <= max;
        }
    }










    
    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }
    public boolean isInitialized() {
        return initialized;
    }


    //Generate Code


    @Override
    public String toString() {
        return "Range [min=" + min + ", max=" + max + ", initialized=" + initialized + "]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(min, max, initialized);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Range))
            return false;
        Range other = (Range) obj;
        return Double.doubleToLongBits(min) == Double.doubleToLongBits(other.min)
                && Double.doubleToLongBits(max) == Double.doubleToLongBits(other.max)
                && initialized == other.initialized;
    }

 




    
}
