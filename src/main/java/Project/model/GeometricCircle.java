package Project.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Project.controller.Transformation;
import javafx.scene.shape.Shape;

public class GeometricCircle extends GeometricShape {
    public double R, centerX, centerY;
    private Circle drawableCircle;
    private Circle drawableHub;

    public GeometricCircle(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableCircle = new Circle();
        drawableHub = new Circle();
        drawableHub.setFill(Color.TRANSPARENT);
        drawableHub.setStrokeWidth(plane.hitbox / 2);
    }

    @Override
    public void updateDrawable() {
        drawableCircle.setFill(Color.TRANSPARENT);
        drawableCircle.setStroke(Color.BLACK);
        drawableCircle.setCenterX(transformation.toScreenX(centerX));
        drawableCircle.setCenterY(transformation.toScreenY(centerY));
        drawableCircle.setRadius(transformation.toScreenX(centerX + R) - transformation.toScreenX(centerX));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableCircle;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public void setOnClicked() {
        drawableHub.setCenterX(transformation.toScreenX(centerX));
        drawableHub.setCenterY(transformation.toScreenY(centerY));
        drawableHub.setRadius(drawableCircle.getRadius());
        drawableHub.setStroke(Color.CYAN);
    }

    @Override
    public void unclick() {
        drawableHub.setStroke(Color.TRANSPARENT);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = GeometricPoint.distance(centerX, centerY, planeX, planeY);
        return Math.abs(d - R) / transformation.scale <= plane.hitbox;
    }

}
