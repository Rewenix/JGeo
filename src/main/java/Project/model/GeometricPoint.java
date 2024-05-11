package Project.model;

import Project.controller.Transformation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GeometricPoint extends GeometricShape {
    private static final double R = 4;
    public double x, y;
    private Circle drawableShape;
    private Circle drawableHub;

    public GeometricPoint(String name, Plane2D plane, Transformation transformation, double planeX, double planeY) {
        super(name, plane, transformation);
        x = planeX;
        y = planeY;
        drawableShape = new Circle(transformation.toScreenX(x), transformation.toScreenY(y), R);
        drawableHub = new Circle(transformation.toScreenX(x), transformation.toScreenY(y), plane.hitbox);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    public GeometricPoint(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableShape = new Circle(0, 0, R);
        drawableHub = new Circle(0, 0, 2 * plane.hitbox / 3);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    @Override
    public void updateDrawable() {
        drawableShape.setCenterX(transformation.toScreenX(x));
        drawableShape.setCenterY(transformation.toScreenY(y));
        drawableHub.setCenterX(transformation.toScreenX(x));
        drawableHub.setCenterY(transformation.toScreenY(y));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableShape;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        return distance(x, y, planeX, planeY) / transformation.scale <= plane.hitbox;
    }

    @Override
    public void setOnClicked() {
        drawableHub.setFill(Color.CYAN);
    }

    @Override
    public void unclick() {
        drawableHub.setFill(Color.TRANSPARENT);
    }

    // Utility functions

    public static double distance(double x1, double y1, double x2, double y2) {
        double dX = x1 - x2;
        double dY = y1 - y2;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public static double distance(GeometricPoint p1, GeometricPoint p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }
}
