package Project.controller.builders.circles;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

/**
 * A builder class for creating a circle with a center point and another point
 * on the circumference.
 */
public class CircleWithCenterAndPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint center = null;
    private GeometricPoint point = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (center == null) {
                center = p;
                return true;
            }
            else if (p != center) {
                point = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return center != null && point != null;
    }

    @Override
    public void reset() {
        center = null;
        point = null;
    }

    @Override
    public boolean awaitsPoint() {
        return point == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricCircle circle = new GeometricCircle();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pCenter = center;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setCircle(circle, pCenter, pPoint);
            }
        };
        circle.setUpdater(updater);
        BuilderUtils.addToPlane(circle, viewablePlane);
    }

    public static void setCircle(GeometricCircle circle, BasicPoint center, BasicPoint point) {
        circle.setCoordinates(getCircle(center, point));
    }

    /**
     * Sets the properties of the given circle based on the provided center and
     * point.
     *
     * @param circle The circle to set the properties for.
     * @param center The center point of the circle.
     * @param point  The point on the circumference of the circle.
     */
    public static void setCircle(GeometricCircle circle, GeometricPoint center, GeometricPoint point) {
        setCircle(circle, center.point, point.point);
    }

    public static BasicCircle getCircle(BasicPoint center, BasicPoint point) {
        double R = BasicPoint.distance(center, point);
        return new BasicCircle(center, R);
    }
}
