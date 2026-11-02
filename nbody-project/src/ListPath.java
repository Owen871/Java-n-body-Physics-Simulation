import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class ListPath implements IPath {

    private List<Posn> points;

    public ListPath() {
        this.points = new ArrayList<>();
    }


    public void addPoint(Posn point) {
        points.add(point);
    }

    public PApplet draw(PApplet w, ITransform t) {
        if (points.size() < 2) {
            return w;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            Posn p1 = t.apply(points.get(i));
            Posn p2 = t.apply(points.get(i + 1));
            w.line((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY());
        }

        return w;
    }


    public boolean samePoints(Posn[] match) {

        if (match.length != points.size()) {
            return false;
        }

        for (int i = 0; i < match.length; i++) {
            Posn p1 = points.get(i);
            Posn p2 = match[i];
            if (!p1.equals(p2)) {
                return false;
            }
        }

        return true;
    }


    @Override
    public String toString() {
        return "ListPath [points=" + points + "]";
    }

    
}
