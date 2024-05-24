package Project.model;

import Project.controller.builders.LineAndCircleIntersectionBuilder;
import Project.controller.builders.LineThroughPointsBuilder;

import java.util.List;

public class GeometricCircle extends GeometricShape {
    public final BasicCircle circle = new BasicCircle();

    public GeometricCircle(String name) {
        super(name);
    }

    public GeometricCircle() {
        super();
    }

    @Override
    public double distance(BasicPoint point) {
        return Math.abs(circle.center.distance(point) - circle.radius);
    }

    public void setCoordinates(BasicCircle circle) {
        this.circle.setCoordinates(circle);
    }

    public boolean isDefined() {
        return circle.isDefined();
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

    @Override
    public int getPriority() {
        return 2;
    }
}