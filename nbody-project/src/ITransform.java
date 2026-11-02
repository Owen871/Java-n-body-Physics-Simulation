/*
 * Owen Gagnon, 10/9/2025
 */

/**
 * Represents a graphics transformation that can be apply to (x, y) coordinates
 */
public interface ITransform {

    /**
     * Produce the result of applying this transform to the given posn
     */
    public Posn apply(Posn p);

    /**
     * Produce the inverse transform of this transform
     */
    public ITransform inverse();



}