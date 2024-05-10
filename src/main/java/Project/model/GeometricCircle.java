package Project.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Project.controller.Transformation;
import javafx.scene.shape.Shape;

public class GeometricCircle extends GeometricShape {
    public double R, centerX, centerY;
    private Circle drawableShape;

    public GeometricCircle(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableShape = new Circle();
    }

    @Override
    public void updateDrawable() {
        drawableShape.setFill(Color.TRANSPARENT);
        drawableShape.setStroke(Color.BLACK);
        drawableShape.setCenterX(transformation.toScreenX(centerX));
        drawableShape.setCenterY(transformation.toScreenY(centerY));
        drawableShape.setRadius(transformation.toScreenX(centerX + R) - transformation.toScreenX(centerX));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableShape;
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double dX = centerX - planeX;
        double dY = centerY - planeY;
        return (dX * dX + dY * dY) / (transformation.scale * transformation.scale) <= plane.hitbox * plane.hitbox;
    }

}
