package org.metastringfoundation.healthheatmap;

public class CLI {
    public String parse(String[] args) throws IllegalArgumentException {
        try {
            String absolutePath = new PathManager(args[0]).getAbsolutePath();
            return absolutePath;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You need to call this program with path to csv file as first argument");
            throw new IllegalArgumentException(e);
        }
    }
}
