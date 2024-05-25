package Project.controller.builders.transforms.polars;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.intersections.LineIntersectionBuilder;
import Project.controller.builders.lines.PerpendicularLineBuilder;
import Project.controller.builders.transforms.inversion.PointInversionBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class PoleBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
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
        return line != null && circle != null;
    }

    @Override
    public void reset() {
        line = null;
        circle = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint pole = new GeometricPoint();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricCircle pCircle = circle;

            @Override
            public void update() {
                setPoint(pole, pLine, pCircle);
            }
        };
        pole.setUpdater(updater);
        BuilderUtils.addToPlane(pole, viewablePlane);
    }

    public static void setPoint(GeometricPoint pole, GeometricLine line, GeometricCircle circle) {
        pole.setCoordinates(getPoint(line.line, circle.circle));
    }

    public static void setPoint(GeometricPoint pole, BasicLine line, BasicCircle circle) {
        pole.setCoordinates(getPoint(line, circle));
    }

    public static BasicPoint getPoint(BasicLine line, BasicCircle circle) {
        BasicLine perpendicular = PerpendicularLineBuilder.getLine(line, circle.center);
        BasicPoint intersection = LineIntersectionBuilder.getPoint(line, perpendicular);
        return PointInversionBuilder.getPoint(intersection, circle);
    }
}
