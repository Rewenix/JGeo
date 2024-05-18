package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

/**
 * A builder class for constructing tangent lines from a point to a circle.
 */
public class TangentsFromPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint point;
    private GeometricCircle circle;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (point == null && shape instanceof GeometricPoint p) {
            point = p;
            point.setOnClicked();
            System.out.println("Accepting point");
            return true;
        }
        else if (circle == null && shape instanceof GeometricCircle c) {
            circle = c;
            circle.setOnClicked();
            System.out.println("Accepting circle");
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return point != null && circle != null;
    }

    @Override
    public void reset() {
        point = null;
        circle = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine tangent1 = new GeometricLine("Tangent1", plane, transformation);
        GeometricLine tangent2 = new GeometricLine("Tangent2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint p = point;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setLines(tangent1, tangent2, p, c);
            }
        };
        BuilderUtils.setUpdaterAndAdd(tangent1, updater, viewPane, plane);
        BuilderUtils.setUpdaterAndAdd(tangent2, updater, viewPane, plane);
    }

    public static void setLines(GeometricLine line1, GeometricLine line2, BasicPoint point, BasicCircle circle) {
        double r = circle.radius;
        double a = circle.center.x;
        double b = circle.center.y; // (x-a)^2 + (y-b)^2 = r^2

        if (Math.abs(BasicPoint.distance(point, circle.center) - r) <= BuilderUtils.EPSILON) {
            BasicLine line = LineThroughPointsBuilder.getLine(point, circle.center);
            PerpendicularLineBuilder.setLine(line1, line, point);
            PerpendicularLineBuilder.setLine(line2, line, point);
            return;
        }

        double x1 = point.x;
        double y1 = point.y;

        double x1_ = (r * r * (x1 - a)
                + r * (y1 - b) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + a;

        double y1_ = (r * r * (y1 - b)
                - r * (x1 - a) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + b;

        double x2_ = (r * r * (x1 - a)
                - r * (y1 - b) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + a;

        double y2_ = (r * r * (y1 - b)
                + r * (x1 - a) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + b;

        LineThroughPointsBuilder.setLine(line1, point, new BasicPoint(x1_, y1_));
        LineThroughPointsBuilder.setLine(line2, point, new BasicPoint(x2_, y2_));
    }

    /**
     * Sets the lines representing the tangents from a point to a circle.
     *
     * @param line1  The first tangent line.
     * @param line2  The second tangent line.
     * @param point  The point.
     * @param circle The circle.
     */
    public static void setLines(GeometricLine line1, GeometricLine line2, GeometricPoint point, GeometricCircle circle) {
        setLines(line1, line2, point.point, circle.circle);
    }
}
