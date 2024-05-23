package Project.view;

import Project.model.BasicPoint;
import Project.model.GeometricShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
        viewablePlane.getViewPane().getChildren().removeAll(getDrawableShape(), getDrawableHub());
    }

    public void setOnClicked() {
        getDrawableHub().setStroke(Color.CYAN);
    }

    public void unclick() {
        getDrawableHub().setStroke(Color.TRANSPARENT);
    }

    public boolean hasPoint(double screenX, double screenY) { // to chyba ma sens
        BasicPoint p = new BasicPoint(viewablePlane.transformation.toPlaneX(screenX), viewablePlane.transformation.toPlaneY(screenY));
        double distance = getGeometricShape().distance(p);
        double screenDistance = distance / viewablePlane.transformation.scale;
        return screenDistance <= viewablePlane.hitbox;
    }

    public abstract void updateDrawable();

    public abstract GeometricShape getGeometricShape();
}