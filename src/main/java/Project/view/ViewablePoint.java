package Project.view;

import Project.model.GeometricPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewablePoint extends ViewableShape {
    private static final double R = 4;
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final GeometricPoint point;

    public ViewablePoint(String name, ViewablePlane viewablePlane, GeometricPoint point) {
        super(name, viewablePlane);
        this.point = point;
        drawableShape = new Circle(viewablePlane.transformation.toScreenX(point.point.x), viewablePlane.transformation.toScreenY(point.point.y), R);
        drawableHub = new Circle(viewablePlane.transformation.toScreenX(point.point.x), viewablePlane.transformation.toScreenY(point.point.y), R);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    @Override
    public double getHub() {
        return 8;
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
    public void updateDrawable() {
        drawableShape.setCenterX(viewablePlane.transformation.toScreenX(point.point.x));
        drawableShape.setCenterY(viewablePlane.transformation.toScreenY(point.point.y));
        drawableHub.setCenterX(drawableShape.getCenterX());
        drawableHub.setCenterY(drawableShape.getCenterY());
    }

    @Override
    public GeometricPoint getGeometricShape() {
        return point;
    }
}