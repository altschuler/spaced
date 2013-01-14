package service.resources;

import java.io.File;

public class Sound {
    
    private File file;

    public Sound(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
