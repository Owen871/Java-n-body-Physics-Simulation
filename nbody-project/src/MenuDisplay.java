/** 
* Owen Gagnon, 9/26/25\
*/


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import processing.core.PApplet;
import processing.event.KeyEvent;


public class MenuDisplay implements IWorld {
    private static final String DATA_DIR = "data";
    
    private String[] dataFiles;
    private int selected;
    private String searchPrefix = "";
    private Optional<Timer> keyTimer = Optional.empty();
    
    public MenuDisplay() {
        // read names of all files in the "data/" directory that end with ".nbody.txt"
        File dir = new File(DATA_DIR);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".nbody.txt");
            }
        };
        
        this.dataFiles = dir.list(filter);
        Arrays.sort(this.dataFiles);
        this.selected = 0;    
    }
    
    public IWorld keyPressed(KeyEvent kev) { 
        if(kev.getKey() == CODED ){
            if ( kev.getKeyCode() == UP) {
                if((selected - 1 + dataFiles.length) % dataFiles.length == 0){
                    if(selected == dataFiles.length){
                        selected = 0;
                    }else{
                        selected=dataFiles.length -1;
                    }
                }else{
                    selected = (selected - 1 + dataFiles.length) % dataFiles.length;
                }
                
            }else if( kev.getKeyCode() == DOWN){
                if((selected + 1 + dataFiles.length) % dataFiles.length == 0){
                    if(selected == dataFiles.length-1){
                        System.out.print("made it to the max going up");
                        selected = 0;
                    }else{
                        selected=dataFiles.length-1;
                    }
                    
                }else{
                    selected = (selected + 1 + dataFiles.length) % dataFiles.length;
                    
                }
            }
        }
        return this;
    }
    
    
    public PApplet draw(PApplet w) {
        w.background(255);
        w.fill(255);
        w.textSize(18);
        w.textAlign(PApplet.LEFT, PApplet.TOP);
        /* w.text("Menu Display", w.width / 2, w.height / 2 - 20);
        w.text("Press ENTER to start simulation", w.width / 2, w.height / 2 + 20);
        w.text("Press ESC to quit", w.width / 2, w.height / 2 + 50);
        */
        
        
        
        int visibleCount = 8;   
        int start = selected - 4;
        
        for (int i = 0; i < visibleCount; i++) {
            int idx = (start + i + dataFiles.length) % dataFiles.length;
            
            String displayName = dataFiles[idx].replace(".nbody.txt", "");
            
            float y = 50 + i * 28;
            
            if (idx == selected) {
                w.fill(255, 200, 0);
            } else {
                w.fill(0);
            }
            
            w.text(displayName, 50, y);
        }
        showFileOverview(w, dataFiles[selected]);
        w.textSize(18);
        w.text("Select a Simulation File:", 50, 25);
        return w;
        
    }
    
    /** Shows the <overview> metadata lines of the selected file */
    private void showFileOverview(PApplet w, String fileName) {
        try {
            Path path = Path.of(DATA_DIR + "/" + fileName);
            List<String> lines = Files.readAllLines(path);
            
            boolean inOverview = false;
            int lineCount = 0;
            
            w.fill(0);
            w.textSize(14);
            
            for (String line : lines) {
                if (line.startsWith("<overview>")) {
                    inOverview = true;
                } 
                else if (inOverview) {
                    w.text(line, 50, 300 + lineCount * 20);
                    lineCount++;
                }
            }
        }
        catch (IOException e) {
            w.fill(255, 0, 0);
            w.text("Error loading file overview.", 250, 100);
        }
    }

    
    /**
     * Helper method for keyevents on handling search within 750 msecs later
     */
    private void handlePrefixSearch(char key) {

    searchPrefix += Character.toLowerCase(key);

    if (keyTimer.isPresent()) {
        keyTimer.get().cancel();
    }

    Timer t = new Timer();

    t.schedule(new TimerTask() {
        public void run() {
            searchPrefix = "";
        }
    }, 750);

    keyTimer = Optional.of(t);


    int n = dataFiles.length;

    String prefixLower = searchPrefix.toLowerCase();

    for (int i = 0; i < n; i++) {

        int idx = (selected + i) % n;

        String baseName = dataFiles[idx].replace(".nbody.txt", "").toLowerCase();

        if (baseName.startsWith(prefixLower)) {
            selected = idx;
            break;
        }
    }
}
    
    
    
    
    /**  produces an updated view based on the key event. 
    * The updated view may be the this object unchanged, an updated/modified version of this, or even a completely different type of IWorld object. 
    * This enables switching from one view to another, for example, when a certain key is pressed.*/
    /*public IWorld keyTyped(KeyEvent kev) { 
        if (kev.getKey() == IWorld.ESCAPE) {
            return Main.instance.forceExit();
        } else if (kev.getKey() == '\n') {
            String filePath = DATA_DIR + "/" + dataFiles[selected];
            return new SimWorld(filePath);
        }else if (Character.isAlphabetic(kev.getKey())) {
            handlePrefixSearch(kev.getKey());
            return this;
        }
        return this;
    }*/

    public IWorld keyTyped(KeyEvent kev) {
    char key = kev.getKey();
    int keyCode = kev.getKeyCode();

    // ESC
    if (key == IWorld.ESCAPE) {
        return Main.instance.forceExit();
    }

    // ENTER loads selected file
    if (key == '\n') {
        String filePath = DATA_DIR + "/" + dataFiles[selected];
        return new SimWorld(filePath);
    }

    // UP
    if (keyCode == PApplet.UP) {
        selected = (selected - 1 + dataFiles.length) % dataFiles.length;
        return this;
    }

    // DOWN
    if (keyCode == PApplet.DOWN) {
        selected = (selected + 1) % dataFiles.length;
        return this;
    }

    // Alphabetic prefix search
    if (Character.isAlphabetic(key)) {
        handlePrefixSearch(key);
        return this;
    }

    return keyDefault(kev);
}

    
    
    
    
    
    // Auto Gen Code
    @Override
    public String toString() {
        return "MenuDisplay []";
    }
    
}
