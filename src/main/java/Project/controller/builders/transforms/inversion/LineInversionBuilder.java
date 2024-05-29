package Project.controller.builders.transforms.inversion;

import Project.Config;
import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.circles.CircleThroughThreePointsBuilder;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

import java.util.ArrayList;
import java.util.List;

public class LineInversionBuilder implements GeometricShapeBuilder {
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
        else if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricLine ll) {
            if (line == null) {
                line = ll;
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
        GeometricGenCircle invertedLine = new GeometricGenCircle();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricCircle pCircle = circle;

            @Override
            public void update() {
                setGenCircle(invertedLine, pLine, pCircle);
                if (invertedLine.circle.isDefined()) invertedLine.line.makeUndefined();
            }
        };
        invertedLine.setUpdater(updater);
        BuilderUtils.addToPlane(invertedLine, viewablePlane);
    }

    public static void setGenCircle(GeometricGenCircle invertedLine, GeometricLine line, GeometricCircle circle) {
        invertedLine.circle.setCoordinates(getCircle(line.line, circle.circle));
        invertedLine.line.setCoordinates(getLine(line.line, circle.circle));
    }

    public static void setGenCircle(GeometricGenCircle invertedLine, BasicLine line, BasicCircle circle) {
        invertedLine.circle.setCoordinates(getCircle(line, circle));
        invertedLine.line.setCoordinates(getLine(line, circle));
    }

    public static BasicCircle getCircle(BasicLine line, BasicCircle circle) {
        BasicPoint p1 = new BasicPoint(100, 100);
        BasicPoint p2 = new BasicPoint(-100, -100);
        BasicPoint p3 = new BasicPoint(-200, -200);
        BasicPoint p4 = new BasicPoint(200, 200);
        if (Math.abs(line.B) < Math.abs(line.A)) {
            p1.x = -(line.C + line.B * p1.y) / line.A;
            p2.x = -(line.C + line.B * p2.y) / line.A;
            p3.x = -(line.C + line.B * p3.y) / line.A;
            p4.x = -(line.C + line.B * p4.y) / line.A;
        }
        else {
            p1.y = -(line.C + line.A * p1.x) / line.B;
            p2.y = -(line.C + line.A * p2.x) / line.B;
            p3.y = -(line.C + line.A * p3.x) / line.B;
            p4.y = -(line.C + line.A * p4.x) / line.B;
        }

        List<BasicPoint> pointsNotOnCenter = new ArrayList<>();
        if (circle.center.distance(p1) > Config.EPSILON) pointsNotOnCenter.add(p1);
        if (circle.center.distance(p2) > Config.EPSILON) pointsNotOnCenter.add(p2);
        if (circle.center.distance(p3) > Config.EPSILON) pointsNotOnCenter.add(p3);
        if (circle.center.distance(p4) > Config.EPSILON) pointsNotOnCenter.add(p4);

        List<BasicPoint> invertedPoints = new ArrayList<>();
        for (BasicPoint point : pointsNotOnCenter) {
            invertedPoints.add(PointInversionBuilder.getPoint(point, circle));
        }

        if (invertedPoints.size() < 3) return new BasicCircle(new BasicPoint(), Double.POSITIVE_INFINITY);

        return CircleThroughThreePointsBuilder.getCircle(invertedPoints.get(0), invertedPoints.get(1), invertedPoints.get(2));
    }

    public static BasicLine getLine(BasicLine line, BasicCircle circle) {
        return line;
    }
}
