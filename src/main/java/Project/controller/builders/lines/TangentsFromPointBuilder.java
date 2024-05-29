package Project.controller.builders.lines;

import Project.Config;
import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

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
            return true;
        }
        else if (circle == null && shape instanceof GeometricCircle c) {
            circle = c;
            return true;
        }
        else if (circle == null && shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            circle = cc;
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
    public boolean awaitsPoint() {
        return point == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine tangent1 = new GeometricLine();
        GeometricLine tangent2 = new GeometricLine();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint p = point;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setLines(tangent1, tangent2, p, c);
            }
        };
        tangent1.setUpdater(updater);
        tangent2.setUpdater(updater);
        BuilderUtils.addToPlane(tangent1, viewablePlane);
        BuilderUtils.addToPlane(tangent2, viewablePlane);
    }

    public static void setLines(GeometricLine line1, GeometricLine line2, BasicPoint point, BasicCircle circle) {
        double r = circle.radius;
        double a = circle.center.x;
        double b = circle.center.y; // (x-a)^2 + (y-b)^2 = r^2

        if (Math.abs(BasicPoint.distance(point, circle.center) - r) <= Config.EPSILON) {
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
