package Project.view.viewable;

import Project.Config;
import Project.model.BasicPoint;
import Project.model.geometricshapes.GeometricPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewablePoint extends ViewableShape {
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final GeometricPoint geoPoint;

    public ViewablePoint(ViewablePlane viewablePlane, GeometricPoint geoPoint) {
        super(viewablePlane);
        this.geoPoint = geoPoint;
        drawableShape = new Circle(getTransformation().toScreenX(geoPoint.point.x), getTransformation().toScreenY(geoPoint.point.y), Config.POINT_RADIUS);
        drawableHub = new Circle(getTransformation().toScreenX(geoPoint.point.x), getTransformation().toScreenY(geoPoint.point.y), Config.POINT_RADIUS);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    @Override
    public double getHub() {
        return Config.POINT_HUB;
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