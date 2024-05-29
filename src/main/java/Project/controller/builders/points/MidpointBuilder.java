package Project.controller.builders.points;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.BasicPoint;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.view.viewable.ViewablePlane;

/**
 * A builder class for creating a midpoint between two points.
 */
public class MidpointBuilder implements GeometricShapeBuilder {
    private GeometricPoint a = null;
    private GeometricPoint b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                return true;
            }
            else if (p != a) {
                b = p;
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
        return a == null || b == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint midpoint = new GeometricPoint();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                setPoint(midpoint, pA, pB);
            }
        };
        midpoint.setUpdater(updater);
        BuilderUtils.addToPlane(midpoint, viewablePlane);
    }

    public static void setPoint(GeometricPoint point, BasicPoint p1, BasicPoint p2) {
        point.setCoordinates(getPoint(p1, p2));
    }

    /**
     * Sets the coordinates of the given point to the midpoint between two other
     * points.
     *
     * @param point The point to set the coordinates for.
     * @param p1    The first point.
     * @param p2    The second point.
     */
    public static void setPoint(GeometricPoint point, GeometricPoint p1, GeometricPoint p2) {
        setPoint(point, p1.point, p2.point);
    }

    public static BasicPoint getPoint(BasicPoint p1, BasicPoint p2) {
        return new BasicPoint((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
}
