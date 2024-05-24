package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.points.PointProjectionOntoLineBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class PointReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricPoint point = null;
    private GeometricLine line = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (point == null) {
                point = p;
                return true;
            }
        }
        else if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricPoint ll) {
            if (point == null) {
                point = ll;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            if (line == null) {
                line = ll;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return point != null && line != null;
    }

    @Override
    public void reset() {
        point = null;
        line = null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint reflectedPoint = new GeometricPoint("Punkt odbity");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setPoint(reflectedPoint, pPoint, pLine);
            }
        };
        reflectedPoint.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedPoint, viewablePlane);
    }

    public static void setPoint(GeometricPoint point, BasicPoint pPoint, BasicLine pLine) {
        point.setCoordinates(getPoint(pPoint, pLine));
    }

    public static void setPoint(GeometricPoint point, GeometricPoint pPoint, GeometricLine pLine) {
        setPoint(point, pPoint.point, pLine.line);
    }

    public static void setPoint(GeometricPoint point, GeometricLine pLine, GeometricPoint pPoint) {
        setPoint(point, pPoint.point, pLine.line);
    }

    public static BasicPoint getPoint(BasicPoint pPoint, BasicLine pLine) { // TODO: check if it works (probably not)
        BasicPoint projection = PointProjectionOntoLineBuilder.getPoint(pPoint, pLine);
        return new BasicPoint(2 * projection.x - pPoint.x, 2 * projection.y - pPoint.y);
    }
}