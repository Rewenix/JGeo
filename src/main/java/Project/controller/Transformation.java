package Project.controller;

import Project.Config;

public class Transformation {
    public double offsetX = 0, offsetY = 0, scale = 1;

    public double toPlaneX(double screenX) {
        return screenX * scale + offsetX;
    }

    public double toPlaneY(double screenY) {
        return -screenY * scale + offsetY;
    }

    public double toScreenX(double planeX) {
        return (planeX - offsetX) / scale;
    }

    public double toScreenY(double planeY) {
        return -(planeY - offsetY) / scale;
    }

    public void changeScale(double screenX, double screenY, double mul) {
        double planeX = toPlaneX(screenX);
        double planeY = toPlaneY(screenY);
        scale = scale * mul;
        if (scale < Config.MIN_SCALE) {
            scale = Config.MIN_SCALE;
        }
        else if (scale > Config.MAX_SCALE) {
            scale = Config.MAX_SCALE;
        }
        offsetX = planeX - scale * screenX;
        offsetY = planeY + scale * screenY;
    }
}
