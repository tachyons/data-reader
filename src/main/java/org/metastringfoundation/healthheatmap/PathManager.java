package org.metastringfoundation.healthheatmap;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathManager {
    private String inputPath;

    public PathManager(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getAbsolutePath() {
        String userDir = System.getProperty("user.dir");
        Path absolutePath = Paths.get(userDir, inputPath);
        String canonicalPath = absolutePath.normalize().toString();
        return canonicalPath;
    }
}
