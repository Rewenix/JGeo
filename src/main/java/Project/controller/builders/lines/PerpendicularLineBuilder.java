package Project.controller.builders.lines;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

/**
 * A builder class for creating perpendicular lines.
 */
public class PerpendicularLineBuilder implements GeometricShapeBuilder {
    private GeometricLine line;
    private GeometricPoint point;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (line == null && shape instanceof GeometricLine l) {
            line = l;
            return true;
        }
        else if (line == null && shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            line = ll;
            return true;
        }
        else if (point == null && shape instanceof GeometricPoint p) {
            point = p;
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return line != null && point != null;
    }

    @Override
    public void reset() {
        line = null;
        point = null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine perpendicularLine = new GeometricLine("Prosta prostopadła");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setLine(perpendicularLine, pLine, pPoint);
            }
        };
        perpendicularLine.setUpdater(updater);
        BuilderUtils.addToPlane(perpendicularLine, viewablePlane);
    }


    public static void setLine(GeometricLine perpendicularLine, GeometricLine line, GeometricPoint point) {
        setLine(perpendicularLine, line.line, point.point);
    }

    public static void setLine(GeometricLine perpendicularLine, GeometricPoint point, GeometricLine line) {
        setLine(perpendicularLine, line.line, point.point);
    }

    public static void setLine(GeometricLine perpendicularLine, BasicLine line, BasicPoint point) {
        perpendicularLine.setCoordinates(getLine(line, point));
    }

    public static BasicLine getLine(BasicLine line, BasicPoint point) {
        return new BasicLine(line.B, -line.A, -line.B * point.x + line.A * point.y);
    }
}
