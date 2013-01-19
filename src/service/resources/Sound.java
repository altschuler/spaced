package service.resources;

import java.io.File;

/**
 * Simply a WAV-{@link File} to be stored and played by {@link SoundHandler}
 */
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
