package Project.controller.builders.intersections;

import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

import java.util.List;
import java.util.Set;

public class CircleIntersectionBuilder implements GeometricIntersectionBuilder {
    private GeometricCircle a = null;
    private GeometricCircle b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (a == null) {
                a = c;
                return true;
            }
            else if (c != a) {
                b = c;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint i1 = new GeometricPoint(a, b);
        GeometricPoint i2 = new GeometricPoint(a, b);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle cA = a;
            private GeometricCircle cB = b;

            @Override
            public void update() {
                setPoints(i1, i2, cA, cB);
            }
        };
        i1.setUpdater(updater);
        i2.setUpdater(updater);
        BuilderUtils.addToPlane(i1, viewablePlane);
        BuilderUtils.addToPlane(i2, viewablePlane);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, BasicCircle c1, BasicCircle c2) {
        List<BasicPoint> points = getPoints(c1, c2);
        i1.setCoordinates(points.get(0));
        i2.setCoordinates(points.get(1));
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle c1, GeometricCircle c2) {
        setPoints(i1, i2, c1.circle, c2.circle);
    }

    public static List<BasicPoint> getPoints(BasicCircle c1, BasicCircle c2) {
        double d = BasicPoint.distance(c1.center, c2.center);
        double r1 = c1.radius;
        double r2 = c2.radius;
        double x1 = c1.center.x;
        double y1 = c1.center.y;
        double x2 = c2.center.x;
        double y2 = c2.center.y;

        double aa = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);
        double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(aa, 2));
        double x3 = x1 + aa * (x2 - x1) / d;
        double y3 = y1 + aa * (y2 - y1) / d;
        BasicPoint i1 = new BasicPoint(x3 + h * (y2 - y1) / d, y3 - h * (x2 - x1) / d);
        BasicPoint i2 = new BasicPoint(x3 - h * (y2 - y1) / d, y3 + h * (x2 - x1) / d);
        return List.of(i1, i2);
    }

    @Override
    public List<GeometricPoint> getIntersections() {
        GeometricPoint i1 = new GeometricPoint();
        GeometricPoint i2 = new GeometricPoint();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle cA = a;
            private GeometricCircle cB = b;

            @Override
            public void update() {
                setPoints(i1, i2, cA, cB);
            }
        };
        i1.setUpdater(updater);
        i2.setUpdater(updater);
        i1.update();
        i2.update();
        reset();
        return List.of(i1, i2);
    }
}
