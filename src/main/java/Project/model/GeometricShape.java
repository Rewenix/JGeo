package Project.model;

import Project.controller.Transformation;
import Project.view.ViewableShape;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.Comparator;

public abstract class GeometricShape {
    protected String name;
    protected Plane2D plane;
    protected Transformation transformation;
    protected GeometricShapeUpdater updater;
    protected ViewableShape viewableShape;

    public GeometricShape(String name, Plane2D plane, Transformation transformation) {
        this.name = name;
        this.plane = plane;
        this.transformation = transformation;
    }

    public void setViewPane(Pane viewPane) {
        viewableShape.setViewPane(viewPane);
    }

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void update() {
        updater.update();
        updateDrawable();
    }

    public void updateDrawable() { viewableShape.updateDrawable(); }

    public Shape getDrawableShape() { return viewableShape.getDrawableShape(); }

    public Shape getDrawableHub() { return viewableShape.getDrawableHub(); }

    public void setOnClicked() { viewableShape.setOnClicked(); }

    public void unclick() { viewableShape.unclick(); }

    public void dropViewable() { viewableShape.drop(); }

    public abstract boolean hasPoint(double planeX, double planeY);

    public abstract BasicPoint projection(BasicPoint point);

    public abstract int getPriority();

    public static Comparator<GeometricShape> getPriorityComparator() {
        return Comparator.comparingInt(GeometricShape::getPriority);
    }
}
