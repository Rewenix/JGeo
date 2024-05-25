package Project.model;

import Project.controller.builders.intersections.LineAndCircleIntersectionBuilder;
import Project.controller.builders.lines.LineThroughPointsBuilder;

import java.util.List;

public class GeometricCircle extends GeometricShape {
    public final BasicCircle circle = new BasicCircle();

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

    public void makeUndefined() {
        circle.center.setCoordinates(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        circle.radius = Double.POSITIVE_INFINITY;
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