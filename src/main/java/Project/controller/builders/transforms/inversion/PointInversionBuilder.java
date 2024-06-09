package Project.controller.builders.transforms.inversion;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

public class PointInversionBuilder implements GeometricShapeBuilder {
    private GeometricPoint point = null;
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (point == null) {
                point = p;
                return true;
            }
        }
        else if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            if (circle == null) {
                circle = cc;
                return true;
            }
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
        GeometricPoint invertedPoint = new GeometricPoint(point, circle);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricCircle pCircle = circle;

            @Override
            public void update() {
                setPoint(invertedPoint, pPoint, pCircle);
            }
        };
        invertedPoint.setUpdater(updater);
        BuilderUtils.addToPlane(invertedPoint, viewablePlane);
    }

    public static void setPoint(GeometricPoint invertedPoint, GeometricPoint point, GeometricCircle circle) {
        invertedPoint.setCoordinates(getPoint(point.point, circle.circle));
    }

    public static void setPoint(GeometricPoint invertedPoint, BasicPoint point, BasicCircle circle) {
        invertedPoint.setCoordinates(getPoint(point, circle));
    }

    public static BasicPoint getPoint(BasicPoint point, BasicCircle circle) { // TODO test
        double x = point.x;
        double y = point.y;
        double r = circle.radius;
        double x0 = circle.center.x;
        double y0 = circle.center.y;
        double d = Math.sqrt((x - x0) * (x - x0) + (y - y0) * (y - y0));
        double x1 = x0 + (r * r * (x - x0)) / (d * d);
        double y1 = y0 + (r * r * (y - y0)) / (d * d);
        return new BasicPoint(x1, y1);
    }
}
