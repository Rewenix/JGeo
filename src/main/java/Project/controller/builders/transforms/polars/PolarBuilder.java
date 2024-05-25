package Project.controller.builders.transforms.polars;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.lines.LineThroughPointsBuilder;
import Project.controller.builders.lines.PerpendicularLineBuilder;
import Project.controller.builders.transforms.inversion.PointInversionBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class PolarBuilder implements GeometricShapeBuilder {
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
        GeometricLine polarLine = new GeometricLine();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricCircle pCircle = circle;

            @Override
            public void update() {
                setLine(polarLine, pPoint, pCircle);
            }
        };
        polarLine.setUpdater(updater);
        BuilderUtils.addToPlane(polarLine, viewablePlane);
    }

    public static void setLine(GeometricLine polarLine, GeometricPoint point, GeometricCircle circle) {
        polarLine.setCoordinates(getLine(point.point, circle.circle));
    }

    public static void setLine(GeometricLine polarLine, BasicPoint point, BasicCircle circle) {
        polarLine.setCoordinates(getLine(point, circle));
    }

    public static BasicLine getLine(BasicPoint point, BasicCircle circle) {
        BasicPoint invertedPoint = PointInversionBuilder.getPoint(point, circle);
        BasicLine line = LineThroughPointsBuilder.getLine(invertedPoint, circle.center);
        return PerpendicularLineBuilder.getLine(line, invertedPoint);
    }
}
