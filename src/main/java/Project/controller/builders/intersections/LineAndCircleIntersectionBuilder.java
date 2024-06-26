package Project.controller.builders.intersections;

import Project.Config;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.points.PointProjectionOntoLineBuilder;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.basicshapes.BasicCircle;
import Project.model.basicshapes.BasicLine;
import Project.model.basicshapes.BasicPoint;
import Project.model.geometricshapes.GeometricCircle;
import Project.model.geometricshapes.GeometricLine;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

import java.util.List;

public class LineAndCircleIntersectionBuilder implements GeometricIntersectionBuilder {
    private GeometricLine line = null;
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                return true;
            }
        }
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return line != null && circle != null;
    }

    @Override
    public void reset() {
        line = null;
        circle = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint intersection1 = new GeometricPoint(line, circle);
        GeometricPoint intersection2 = new GeometricPoint(line, circle);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricLine l = line;
            private final GeometricCircle c = circle;

            @Override
            public void update() {
                intersection1.isMovable = false;
                intersection2.isMovable = false;
                setPoints(intersection1, intersection2, l, c);
            }
        };
        intersection1.setUpdater(updater);
        intersection2.setUpdater(updater);
        BuilderUtils.addToPlane(intersection1, viewablePlane);
        BuilderUtils.addToPlane(intersection2, viewablePlane);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, BasicLine l, BasicCircle c) {
        List<BasicPoint> points = getPoints(l, c);
        i1.setCoordinates(points.get(0));
        i2.setCoordinates(points.get(1));
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricLine l, GeometricCircle c) {
        setPoints(i1, i2, l.line, c.circle);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle c, GeometricLine l) {
        setPoints(i1, i2, l.line, c.circle);
    }

    public static List<BasicPoint> getPoints(BasicLine l, BasicCircle circle) {
        BasicPoint projection = PointProjectionOntoLineBuilder.getPoint(circle.center, l);
        if (Math.abs(projection.distance(circle.center) - circle.radius) <= Config.EPSILON) {
            return List.of(projection, projection);
        }

        double A = l.A;
        double B = l.B;
        double x0 = circle.center.x;
        double y0 = circle.center.y;
        double R = circle.radius;
        if (Math.abs(l.B) <= Config.EPSILON) {
            A = l.B;
            B = l.A;
            x0 = circle.center.y;
            y0 = circle.center.x;
        }

        double a = A * A + B * B;
        double b = 2 * (A * l.C + A * B * y0 - B * B * x0);
        double c = l.C * l.C + 2 * B * l.C * y0 - B * B * (R * R - x0 * x0 - y0 * y0);
        double delta = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
        double y1 = (-A * x1 - l.C) / B;
        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
        double y2 = (-A * x2 - l.C) / B;

        if (Math.abs(l.B) <= Config.EPSILON) {
            double tmp = x1;
            x1 = y1;
            y1 = tmp;
            tmp = x2;
            x2 = y2;
            y2 = tmp;
            if (B >= 0) {
                tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
        }

        else if (B < 0) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        return List.of(new BasicPoint(x1, y1), new BasicPoint(x2, y2));
    }

    @Override
    public List<GeometricPoint> getIntersections() {
        GeometricPoint intersection1 = new GeometricPoint();
        GeometricPoint intersection2 = new GeometricPoint();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricLine l = line;
            private final GeometricCircle c = circle;

            @Override
            public void update() {
                intersection1.isMovable = false;
                intersection2.isMovable = false;
                setPoints(intersection1, intersection2, l, c);
            }
        };
        intersection1.setUpdater(updater);
        intersection2.setUpdater(updater);
        intersection1.update();
        intersection2.update();
        reset();
        return List.of(intersection1, intersection2);
    }
}
