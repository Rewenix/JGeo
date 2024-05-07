package Project.model;

import Project.controller.Transformation;
import javafx.scene.shape.Shape;

public abstract class GeometricShape {
    protected String name;
    protected Plane2D plane;
    protected Transformation transformation;
    protected GeometricShapeUpdater updater;

    public GeometricShape(String name, Plane2D plane, Transformation transformation){
        this.name = name;
        this.plane = plane;
        this.transformation = transformation;
    }

    public void setUpdater(GeometricShapeUpdater updater){
        this.updater = updater;
    }

    public void update(){
        updater.update();
        updateDrawable();
    }

    public abstract void updateDrawable();
    public abstract Shape getDrawableShape();
    public abstract boolean hasPoint(double planeX, double planeY);
}
