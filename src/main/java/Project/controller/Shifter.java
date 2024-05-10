package Project.controller;

import Project.model.GeometricPoint;
import Project.model.Plane2D;

public class Shifter implements Actor {
    private final Plane2D plane;
    private final Transformation transformation;
    protected GeometricPoint point = null;
    double offsetXOnEntry, offsetYOnEntry;
    double vectorOriginX, vectorOriginY; // On Plane2D but it does not matter as long as it is consistent.

    public Shifter(Plane2D plane, Transformation transformation) {
        this.plane = plane;
        this.transformation = transformation;
    }

    public void setPoint(GeometricPoint point) {
        this.point = point;
    }

    public void setOrigin(double screenX, double screenY) {
        vectorOriginX = screenX;
        vectorOriginY = screenY;
        offsetXOnEntry = transformation.offsetX;
        offsetYOnEntry = transformation.offsetY;
    }

    public void shift(double planeX, double planeY) {
        if (point != null) {
            shiftPoint(planeX, planeY);
        } else {
            shiftPlane(planeX, planeY);
        }
        plane.update();
    }

    public void shiftPoint(double planeX, double planeY) {
        point.x = planeX;
        point.y = planeY;
    }

    public void shiftPlane(double planeX, double planeY) {
        transformation.offsetX = offsetXOnEntry - planeX + vectorOriginX;
        transformation.offsetY = offsetYOnEntry - planeY + vectorOriginY;
    }
}
