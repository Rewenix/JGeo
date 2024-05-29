package Project.controller.builders.circles;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.lines.LineThroughPointsBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

/**
 * A builder class for creating a circle through three points.
 */
public class CircleThroughThreePointsBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b, c;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                return true;
            }
            else if (b == null) {
                b = p;
                return true;
            }
            else if (c == null) {
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
        GeometricGenCircle genCircle = new GeometricGenCircle();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                setCircle(genCircle.circle, pA, pB, pC);
                if (!genCircle.circle.isDefined() && pC.isDefined()) {
                    LineThroughPointsBuilder.setLine(genCircle.line, pA.point, pB.point);
                }
                else {
                    genCircle.line.makeUndefined();
                }
            }
        };
        genCircle.setUpdater(updater);
        BuilderUtils.addToPlane(genCircle, viewablePlane);
    }

    public static void setCircle(GeometricCircle circle, BasicPoint a, BasicPoint b, BasicPoint c) {
        circle.setCoordinates(getCircle(a, b, c));
    }

    /**
     * Sets the properties of the circle based on the given points.
     *
     * @param circle The circle to set the properties for.
     * @param a      The first point.
     * @param b      The second point.
     * @param c      The third point.
     */
    public static void setCircle(GeometricCircle circle, GeometricPoint a, GeometricPoint b, GeometricPoint c) {
        setCircle(circle, a.point, b.point, c.point);
    }

    public static BasicCircle getCircle(BasicPoint a, BasicPoint b, BasicPoint c) {
        return BasicCircle.getCircle(a, b, c);
    }
}
