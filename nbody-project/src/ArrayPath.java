 /*
 * Owen Gagnon
 */

import java.util.Arrays;
import java.util.Objects;

import processing.core.PApplet;

public class ArrayPath implements IPath{
     private Posn[] buf;    
    private int tail;      
    private int count;

        public ArrayPath() {
        this.buf = new Posn[0];
        this.tail = 0;
        this.count = 0;
    }  
        public ArrayPath(int maxLength) {
        this.buf = new Posn[maxLength];
        this.tail = 0;
        this.count = 0;
    }  
/**
 * allows us to work through an array in a circular way
 */
    private int wrap(int i) {
     return i % buf.length;
 }


/**
 * adds points to the arrayPath
 */
    public void addPoint(Posn point){
    if (buf.length != AppSettings.getInstance().getMaxPathLength()) {
        buf = new Posn[AppSettings.getInstance().getMaxPathLength()];
        tail = 0;
        count = 0;
    }

    if (AppSettings.getInstance().getMaxPathLength() == 0) {
        return; 
    }
    
    buf[(tail + count) % buf.length] = point;

    if (count < buf.length) {
        count++;
    } else {
        tail = (tail + 1) % buf.length;
    }
    }




    /**
     * draws the arrayPath
     */
     public PApplet draw(PApplet w, ITransform t) {
        if (count <= 1) {
            return w; 
        }

        w.stroke(255);
        for (int i = 0; i < count - 1; i++) {
            int nextIdx = wrap(tail + i);
            //System.out.println("nextidx: " + nextIdx);
            Posn p1 = t.apply(buf[nextIdx]);
            Posn p2 = t.apply(buf[wrap(nextIdx+1)]);

            w.line((float)p1.getX(), (float)p1.getY(),
                (float)p2.getX(), (float)p2.getY());

        }

        return w;
    }

/**
 * checks if two points are the same or not
 */
    public boolean samePoints(Posn[] match) {
        if (match.length != count) return false;

        for (int i = 0; i < count; i++) {
            if (!buf[ wrap(tail + i)].equals(match[i])) {
                return false;
            }
        }
        return true;
    }



    //Auto Gen code
@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(buf);
    result = prime * result + Objects.hash(tail, count);
    return result;
}
@Override
public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (!(obj instanceof ArrayPath))
        return false;
    ArrayPath other = (ArrayPath) obj;
    return Arrays.equals(buf, other.buf) && tail == other.tail && count == other.count;
}
@Override
public String toString() {
    return "ArrayPath [buf=" + Arrays.toString(buf) + ", tail=" + tail + ", count=" + count + "]";
}

}



