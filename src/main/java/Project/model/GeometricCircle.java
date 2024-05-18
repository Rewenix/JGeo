package Project.model;

import Project.controller.Transformation;
import Project.controller.builders.LineAndCircleIntersectionBuilder;
import Project.controller.builders.LineThroughPointsBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.List;

public class GeometricCircle extends GeometricShape {
    private static final double hub = 4;
    public BasicCircle circle = new BasicCircle();
    private final Circle drawableCircle;
    private final Circle drawableHub;

    @Override
    public int getPriority() {
        return 2;
    }

    public GeometricCircle(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableCircle = new Circle();
        drawableHub = new Circle();
        drawableHub.setFill(Color.TRANSPARENT);
        drawableHub.setStrokeWidth(hub);
    }

    @Override
    public void updateDrawable() {
        drawableCircle.setFill(Color.TRANSPARENT);
        drawableCircle.setStroke(Color.BLACK);
        drawableCircle.setCenterX(transformation.toScreenX(circle.center.x));
        drawableCircle.setCenterY(transformation.toScreenY(circle.center.y));
        drawableCircle.setRadius(transformation.toScreenX(circle.center.x + circle.radius) - transformation.toScreenX(circle.center.x));
    }

    public void setCoordinates(BasicCircle circle) {
        this.circle = circle;
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
        drawableHub.setCenterX(transformation.toScreenX(circle.center.x));
        drawableHub.setCenterY(transformation.toScreenY(circle.center.y));
        drawableHub.setRadius(drawableCircle.getRadius());
        drawableHub.setStroke(Color.CYAN);
    }

    @Override
    public void unclick() {
        drawableHub.setStroke(Color.TRANSPARENT);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = circle.center.distance(new BasicPoint(planeX, planeY));
        return Math.abs(d - circle.radius) / transformation.scale <= plane.hitbox;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        BasicLine line = LineThroughPointsBuilder.getLine(circle.center, point);
        List<BasicPoint> intersections = LineAndCircleIntersectionBuilder.getPoints(line, circle);
        if (point.distance(intersections.get(0)) < point.distance(intersections.get(1))) {
            return intersections.get(0);
        }
        return intersections.get(1);
    }
}
