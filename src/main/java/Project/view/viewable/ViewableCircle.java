package Project.view.viewable;

import Project.Config;
import Project.model.GeometricShape;
import Project.model.basicshapes.BasicCircle;
import Project.model.geometricshapes.GeometricCircle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewableCircle extends ViewableShape {
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final GeometricCircle geoCircle;

    public ViewableCircle(ViewablePlane viewablePlane, GeometricCircle geoCircle) {
        super(viewablePlane);
        this.geoCircle = geoCircle;
        drawableShape = new Circle();
        drawableShape.setFill(Color.TRANSPARENT);
        drawableShape.setStroke(Color.BLACK);
        drawableHub = new Circle();
        drawableHub.setFill(Color.TRANSPARENT);
    }

    @Override
    public double getHub() {
        return Config.CIRCLE_HUB;
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
        BasicCircle circle = geoCircle.circle;
        drawableShape.setCenterX(getTransformation().toScreenX(circle.center.x));
        drawableShape.setCenterY(getTransformation().toScreenY(circle.center.y));
        drawableShape.setRadius(getTransformation().toScreenX(circle.center.x + circle.radius) - getTransformation().toScreenX(circle.center.x));
        drawableHub.setCenterX(drawableShape.getCenterX());
        drawableHub.setCenterY(drawableShape.getCenterY());
        drawableHub.setRadius(drawableShape.getRadius());
    }

    @Override
    public GeometricShape getGeometricShape() {
        return geoCircle;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
