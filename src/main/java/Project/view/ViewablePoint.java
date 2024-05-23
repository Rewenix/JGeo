package Project.view;

import Project.model.BasicPoint;
import Project.model.GeometricPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewablePoint extends ViewableShape {
    private static final double R = 4;
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final GeometricPoint geoPoint;

    public ViewablePoint(String name, ViewablePlane viewablePlane, GeometricPoint geoPoint) {
        super(name, viewablePlane);
        this.geoPoint = geoPoint;
        drawableShape = new Circle(getTransformation().toScreenX(geoPoint.point.x), getTransformation().toScreenY(geoPoint.point.y), R);
        drawableHub = new Circle(getTransformation().toScreenX(geoPoint.point.x), getTransformation().toScreenY(geoPoint.point.y), R);
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
        BasicPoint point = geoPoint.point;
        drawableShape.setCenterX(getTransformation().toScreenX(point.x));
        drawableShape.setCenterY(getTransformation().toScreenY(point.y));
        drawableHub.setCenterX(drawableShape.getCenterX());
        drawableHub.setCenterY(drawableShape.getCenterY());
    }

    @Override
    public GeometricPoint getGeometricShape() {
        return geoPoint;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}