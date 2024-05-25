package Project.controller.builders.lines;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.points.MidpointBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

/**
 * A builder class for creating a perpendicular bisector of two points.
 */
public class PerpendicularBisectorBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b;

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
        return b == null || a == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Symetralna");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                setLine(line, pA, pB);
            }
        };
        line.setUpdater(updater);
        BuilderUtils.addToPlane(line, viewablePlane);
    }

    public static void setLine(GeometricLine line, BasicPoint p1, BasicPoint p2) {
        line.setCoordinates(getLine(p1, p2));
    }

    public static void setLine(GeometricLine line, GeometricPoint p1, GeometricPoint p2) {
        setLine(line, p1.point, p2.point);
    }

    public static BasicLine getLine(BasicPoint p1, BasicPoint p2) {
        BasicPoint mid = MidpointBuilder.getPoint(p1, p2);
        BasicLine line1 = LineThroughPointsBuilder.getLine(p1, p2);
        return PerpendicularLineBuilder.getLine(line1, mid);
    }
}
