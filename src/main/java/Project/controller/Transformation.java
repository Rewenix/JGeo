package Project.controller;

public class Transformation {
    public double offsetX = 0, offsetY = 0, scale = 1;

    public double toPlaneX(double screenX) {
        return screenX * scale + offsetX;
    }

    public double toPlaneY(double screenY) {
        return screenY * scale + offsetY;
    }

    public double toScreenX(double planeX) {
        return (planeX - offsetX) / scale;
    }

    public double toScreenY(double planeY) {
        return (planeY - offsetY) / scale;
    }
}
