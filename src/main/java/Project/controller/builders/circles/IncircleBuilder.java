package Project.controller.builders.circles;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

public class IncircleBuilder implements GeometricShapeBuilder {
    private GeometricPoint a = null;
    private GeometricPoint b = null;
    private GeometricPoint c = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                return true;
            }
            else if (b == null && p != a) {
                b = p;
                return true;
            }
            else if (c == null && p != a && p != b) {
                c = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null && c != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
        c = null;
    }

    @Override
    public boolean awaitsPoint() {
        return c == null || b == null || a == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricCircle incircle = new GeometricCircle();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                setCircle(incircle, pA, pB, pC);
            }
        };
        incircle.setUpdater(updater);
        BuilderUtils.addToPlane(incircle, viewablePlane);
    }

    public static void setCircle(GeometricCircle circle, GeometricPoint a, GeometricPoint b, GeometricPoint c) {
        circle.setCoordinates(getCircle(a.point, b.point, c.point));
    }

    public static void setCircle(GeometricCircle circle, BasicPoint a, BasicPoint b, BasicPoint c) {
        circle.setCoordinates(getCircle(a, b, c));
    }

    public static BasicCircle getCircle(BasicPoint a, BasicPoint b, BasicPoint c) {
        double AB = a.distance(b);
        double BC = b.distance(c);
        double CA = c.distance(a);
        double p = AB + BC + CA;
        double x = (BC * a.x + CA * b.x + AB * c.x) / p;
        double y = (BC * a.y + CA * b.y + AB * c.y) / p;
        double s = p / 2;
        double r = Math.sqrt((s - AB) * (s - BC) * (s - CA) / s);
        BasicPoint center = new BasicPoint(x, y);
        return new BasicCircle(center, r);
    }
}
