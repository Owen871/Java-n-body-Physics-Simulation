 /*
 * Owen Gagnon
 */
 
 
 
 /*
  * Represents a trail of previous positions of a Body
  */

import processing.core.PApplet;

public interface IPath {
     void addPoint(Posn point);
     PApplet draw(PApplet w, ITransform t);
     boolean samePoints(Posn[] match);
 }