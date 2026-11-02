/*
 * Owen Gagnon 10/16/25
 */


public class AppSettings {
    
    private static AppSettings instance =null;

    private boolean showLabels;
    boolean showPaths;
    private int maxPathLength;
    public boolean showHelp = false;

    private AppSettings(){
        this.showLabels = true;
        this.showPaths = true;
        this.maxPathLength = 100;
    }

    public static AppSettings getInstance(){
        if(instance==null){
            instance = new AppSettings();
        }
            return instance;
    }

    //setter and getter for showLabels
    public boolean showLabels(){
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    //setter and getter methods for ShowPaths and MaxPathLength

    public boolean getShowPaths() {
        return showPaths;
    }

    public int getMaxPathLength() {
        return maxPathLength;
    }

    public void setShowPaths(boolean showPaths) {
        this.showPaths = showPaths;
    }

    public void setMaxPathLength(int maxPathLength) {
        this.maxPathLength = maxPathLength;
    }
    



    

}
