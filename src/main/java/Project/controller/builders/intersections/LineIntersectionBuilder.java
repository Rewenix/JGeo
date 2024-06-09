package Project.controller.builders.intersections;

import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.model.basicshapes.BasicLine;
import Project.model.basicshapes.BasicPoint;
import Project.model.geometricshapes.GeometricGenCircle;
import Project.model.geometricshapes.GeometricLine;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

import java.util.List;

public class LineIntersectionBuilder implements GeometricIntersectionBuilder {
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
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint intersection = new GeometricPoint(a, b);
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

    @Override
    public List<GeometricPoint> getIntersections() {
        GeometricPoint intersection = new GeometricPoint(a, b);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine lA = a;
            private GeometricLine lB = b;

            @Override
            public void update() {
                setPoint(intersection, lA, lB);
            }
        };
        intersection.setUpdater(updater);
        intersection.update();
        reset();
        return List.of(intersection);
    }
}
