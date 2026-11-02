/**
 * Owen Gagnon
 */



public class Color {
    public int red;
    public int green;
    public int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
    * Uses Processing's PApplet.color(...) method to pack the RGB 
    * components (each in 0-255 range) into a single int, suitable
    * for use with PApplet drawing methods (stroke(), fill()).
    */
    public int asInt() {
        // Main.instance is your PApplet
        return Main.instance.color(red, green, blue);
    }
}
