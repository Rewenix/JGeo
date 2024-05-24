package Project.controller;

import Project.model.GeometricPoint;
import Project.model.Plane2D;
import Project.view.ViewablePlane;

public class Shifter implements Actor {
    private final ViewablePlane viewablePlane;
    private final Transformation transformation;
    protected GeometricPoint point = null;
    double offsetXOnEntry, offsetYOnEntry;
    double vectorOriginX, vectorOriginY; // On Plane2D but it does not matter as long as it is consistent.

    public Shifter(ViewablePlane viewablePlane) {
        this.viewablePlane = viewablePlane;
        this.transformation = viewablePlane.getTransformation();
    }

    public void setPoint(GeometricPoint point) {
        this.point = point;
    }

    public void setOrigin(double planeX, double planeY) {
        vectorOriginX = planeX;
        vectorOriginY = planeY;
        offsetXOnEntry = transformation.offsetX;
        offsetYOnEntry = transformation.offsetY;
    }

    public void shift(double planeX, double planeY) {
        if (point != null) {
            shiftPoint(planeX, planeY);
        }
        else {
            shiftPlane(planeX, planeY);
        }
        viewablePlane.getPlane().update();
        viewablePlane.updateDrawables();
    }

    public void shiftPoint(double planeX, double planeY) {
        point.point.x = planeX;
        point.point.y = planeY;
    }

    public void shiftPlane(double planeX, double planeY) {
        transformation.offsetX = offsetXOnEntry - planeX + vectorOriginX;
        transformation.offsetY = offsetYOnEntry - planeY + vectorOriginY;
    }
}
