package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class LineIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricLine a = null;
    private GeometricLine b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (a == null) {
                a = l;
                return true;
            }
            else if (l != a) {
                b = l;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            if (a == null) {
                a = ll;
                return true;
            }
            else if (ll != a) {
                b = ll;
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
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint intersection = new GeometricPoint("Przecięcie");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine lA = a;
            private GeometricLine lB = b;

            @Override
            public void update() {
                setPoint(intersection, lA, lB);
            }
        };
        intersection.setUpdater(updater);
        BuilderUtils.addToPlane(intersection, viewablePlane);
    }

    public static void setPoint(GeometricPoint point, BasicLine lA, BasicLine lB) {
        point.setCoordinates(getPoint(lA, lB));
    }

    public static void setPoint(GeometricPoint point, GeometricLine lA, GeometricLine lB) {
        setPoint(point, lA.line, lB.line);
    }

    public static BasicPoint getPoint(BasicLine lA, BasicLine lB) {
        return new BasicPoint((lA.B * lB.C - lB.B * lA.C) / (lA.A * lB.B - lB.A * lA.B),
                (lA.C * lB.A - lB.C * lA.A) / (lA.A * lB.B - lB.A * lA.B));
    }
}
