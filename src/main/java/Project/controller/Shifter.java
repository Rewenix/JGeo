package Project.controller;

import Project.model.GeometricPoint;
import Project.view.viewable.ViewablePlane;
import Project.view.viewable.ViewablePoint;

public class Shifter implements Actor { // TODO consider splitting this class into two classes.
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

    @Override
    public void handleClick(ViewablePlane viewablePlane, double screenX, double screenY) {
        viewablePlane.unclickAll();
        if (viewablePlane.getClickedShape(screenX, screenY) instanceof ViewablePoint point) {
            setPoint(point.getGeometricShape());
            point.setOnClicked();
            return;
        }
        setPoint(null);
        setOrigin(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
    }

    public void handleDragged(double screenX, double screenY) {
        shift(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
        setOrigin(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
    }
}
