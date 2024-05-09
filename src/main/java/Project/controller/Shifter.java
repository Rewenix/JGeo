package Project.controller;

import Project.model.Plane2D;
import Project.model.Point;

public class Shifter implements Actor{
    private final Plane2D plane;
    protected Point point = null;
    double vectorOriginX, vectorOriginY;    // On Plane2D but it does not matter as long as it is consistent.

    public Shifter(Plane2D plane){
        this.plane = plane;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void setVectorOrigin(double screenX, double screenY){
        vectorOriginX = screenX;
        vectorOriginY = screenY;
    }

    public void shift(double planeX, double planeY) {
        if(point != null){
            shiftPoint(planeX, planeY);
        }
        plane.update();
    }

    public void shiftPoint(double planeX, double planeY) {
        point.x = planeX;
        point.y = planeY;
    }
}
