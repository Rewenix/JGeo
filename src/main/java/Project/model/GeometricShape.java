package Project.model;

import Project.controller.Transformation;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class GeometricShape {
    protected String name;
    protected Plane2D plane;
    protected Transformation transformation;
    protected Pane viewPane;
    protected GeometricShapeUpdater updater;

    public GeometricShape(String name, Plane2D plane, Transformation transformation) {
        this.name = name;
        this.plane = plane;
        this.transformation = transformation;
    }

    public void setViewPane(Pane viewPane) {
        this.viewPane = viewPane;
        viewPane.getChildren().addAll(getDrawableShape(), getDrawableHub());
        getDrawableHub().toBack();
    }

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void update() {
        updater.update();
        updateDrawable();
    }

    public abstract void updateDrawable();

    public abstract Shape getDrawableShape();

    public abstract Shape getDrawableHub();

    public abstract void setOnClicked();

    public abstract void unclick();

    public abstract boolean hasPoint(double planeX, double planeY);
}
