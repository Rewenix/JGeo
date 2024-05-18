package Project.model;

import Project.controller.Transformation;
import Project.controller.builders.LineAndCircleIntersectionBuilder;
import Project.controller.builders.LineThroughPointsBuilder;
import Project.view.ViewableCircle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.List;

public class GeometricCircle extends GeometricShape {
    public final BasicCircle circle = new BasicCircle();

    @Override
    public int getPriority() {
        return 2;
    }

    public GeometricCircle(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        viewableShape = new ViewableCircle(name, transformation, circle);
    }

    public void setCoordinates(BasicCircle circle) {
        this.circle.setCoordinates(circle);
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
