package Project.model;

import Project.controller.Transformation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GeometricPoint extends GeometricShape {
    private static final double R = 4;
    private static final double hub = 2 * R;
    public BasicPoint point;
    private final Circle drawableShape;
    private final Circle drawableHub;

    static {
        priority = 0;
    }

    public GeometricPoint(String name, Plane2D plane, Transformation transformation, double planeX, double planeY) {
        super(name, plane, transformation);
        point = new BasicPoint(planeX, planeY);
        drawableShape = new Circle(transformation.toScreenX(point.x), transformation.toScreenY(point.y), R);
        drawableHub = new Circle(transformation.toScreenX(point.x), transformation.toScreenY(point.y), hub);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    public GeometricPoint(String name, Plane2D plane, Transformation transformation) {
        this(name, plane, transformation, 0, 0);
    }

    public void setCoordinates(BasicPoint point) {
        this.point = point;
    }

    @Override
    public void updateDrawable() {
        drawableShape.setCenterX(transformation.toScreenX(point.x));
        drawableShape.setCenterY(transformation.toScreenY(point.y));
        drawableHub.setCenterX(transformation.toScreenX(point.x));
        drawableHub.setCenterY(transformation.toScreenY(point.y));
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
        return BasicPoint.distance(point, new BasicPoint(planeX, planeY)) / transformation.scale <= plane.hitbox;
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

    public static double distance(GeometricPoint p1, GeometricPoint p2) {
        return BasicPoint.distance(p1.point, p2.point);
    }
}
