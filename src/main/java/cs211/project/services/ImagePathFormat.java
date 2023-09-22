package cs211.project.services;

public class ImagePathFormat {

    String path;

    public ImagePathFormat(String path) {
        if (path.startsWith("x")) {
            this.path = getClass().getResource(path.substring(1)).toString();
            return;
        }
        this.path = "file:" + path;
    }

    @Override
    public String toString() {
        return path;
    }
}
