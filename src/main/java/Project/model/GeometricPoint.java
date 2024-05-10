package Project.model;

import Project.controller.Transformation;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GeometricPoint extends GeometricShape {
    private static final double R = 4;
    public double x, y;
    private Circle drawableShape;

    public GeometricPoint(String name, Plane2D plane, Transformation transformation, double planeX, double planeY) {
        super(name, plane, transformation);
        x = planeX;
        y = planeY;
        drawableShape = new Circle(transformation.toScreenX(x), transformation.toScreenY(y), R);
    }

    @Override
    public void updateDrawable() {
        drawableShape.setCenterX(transformation.toScreenX(x));
        drawableShape.setCenterY(transformation.toScreenY(y));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableShape;
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double dX = x - planeX;
        double dY = y - planeY;
        return (dX * dX + dY * dY) / (transformation.scale * transformation.scale) <= plane.hitbox * plane.hitbox;
    }
}
