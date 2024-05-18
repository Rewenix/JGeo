package Project.model;

import Project.controller.Transformation;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.Comparator;

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

    public static Comparator<GeometricShape> getPriorityComparator() {
        return (shape1, shape2) -> {
            if (shape1 instanceof GeometricPoint && shape2 instanceof GeometricPoint) {
                return 0;
            } else if (shape1 instanceof GeometricPoint) {
                return -1;
            } else if (shape2 instanceof GeometricPoint) {
                return 1;
            } else if (shape1 instanceof GeometricSegment && shape2 instanceof GeometricSegment) {
                return 0;
            } else if (shape1 instanceof GeometricSegment) {
                return -1;
            } else if (shape2 instanceof GeometricSegment) {
                return 1;
            } else {
                return 0;
            }
        };
    }
}
