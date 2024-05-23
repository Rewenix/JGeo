package Project.view;

import Project.controller.Transformation;
import Project.model.BasicPoint;
import Project.model.GeometricShape;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Comparator;

public abstract class ViewableShape {
    public final String name;
    final ViewablePlane viewablePlane;

    public ViewableShape(String name, ViewablePlane viewablePlane) {
        this.name = name;
        this.viewablePlane = viewablePlane;
    }

    public abstract double getHub();

    public abstract Shape getDrawableShape();

    public abstract Shape getDrawableHub();

    public void drop() {
        viewablePlane.viewPane.getChildren().removeAll(getDrawableShape(), getDrawableHub());
    }

    public void setOnClicked() {
        getDrawableHub().setStroke(Color.CYAN);
    }

    public void unclick() {
        getDrawableHub().setStroke(Color.TRANSPARENT);
    }

    public void setViewPane(Pane viewPane) {
        getDrawableHub().setStroke(Color.TRANSPARENT);
        getDrawableHub().setStrokeWidth(getHub());
        viewPane.getChildren().addAll(getDrawableShape(), getDrawableHub());
        getDrawableHub().toBack();
    }

    public boolean hasPoint(double screenX, double screenY) { // to chyba ma sens
        BasicPoint p = new BasicPoint(viewablePlane.transformation.toPlaneX(screenX), viewablePlane.transformation.toPlaneY(screenY));
        double distance = getGeometricShape().distance(p);
        double screenDistance = distance / viewablePlane.transformation.scale;
        return screenDistance <= ViewablePlane.hitbox;
    }

    public abstract void updateDrawable();

    public abstract GeometricShape getGeometricShape();

    public abstract int getPriority();

    public static Comparator<ViewableShape> getPriorityComparator() {
        return Comparator.comparingInt(ViewableShape::getPriority);
    }

    protected Transformation getTransformation() {
        return viewablePlane.transformation;
    }
}