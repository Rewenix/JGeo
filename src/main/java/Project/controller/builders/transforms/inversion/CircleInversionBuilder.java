package Project.controller.builders.transforms.inversion;

import Project.Config;
import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.circles.CircleThroughThreePointsBuilder;
import Project.controller.builders.lines.LineThroughPointsBuilder;
import Project.model.*;
import Project.model.geometricshapes.GeometricCircle;
import Project.model.geometricshapes.GeometricGenCircle;
import Project.view.viewable.ViewablePlane;

import java.util.ArrayList;
import java.util.List;

public class CircleInversionBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;
    private GeometricCircle inversionCircle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                return true;
            }
            else if (inversionCircle == null) {
                inversionCircle = c;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            if (circle == null) {
                circle = cc;
                return true;
            }
            else if (inversionCircle == null) {
                inversionCircle = cc;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return circle != null && inversionCircle != null;
    }

    @Override
    public void reset() {
        circle = null;
        inversionCircle = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricGenCircle invertedCircle = new GeometricGenCircle(circle, inversionCircle);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle pCircle = circle;
            private GeometricCircle pInversionCircle = inversionCircle;

            @Override
            public void update() {
                setGenCircle(invertedCircle, pCircle, pInversionCircle);
                if (invertedCircle.circle.isDefined()) invertedCircle.line.makeUndefined();
            }
        };
        invertedCircle.setUpdater(updater);
        BuilderUtils.addToPlane(invertedCircle, viewablePlane);
    }

    public static void setGenCircle(GeometricGenCircle invertedCircle, GeometricCircle circle, GeometricCircle inversionCircle) {
        invertedCircle.circle.setCoordinates(getCircle(circle.circle, inversionCircle.circle));
        invertedCircle.line.setCoordinates(getLine(circle.circle, inversionCircle.circle));
    }

    public static void setGenCircle(GeometricGenCircle invertedCircle, BasicCircle circle, BasicCircle inversionCircle) {
        invertedCircle.circle.setCoordinates(getCircle(circle, inversionCircle));
        invertedCircle.line.setCoordinates(getLine(circle, inversionCircle));
    }

    public static BasicLine getLine(BasicCircle circle, BasicCircle inversionCircle) {
        BasicPoint p1 = new BasicPoint(circle.center.x + circle.radius, circle.center.y);
        BasicPoint p2 = new BasicPoint(circle.center.x - circle.radius, circle.center.y);
        BasicPoint p3 = new BasicPoint(circle.center.x, circle.center.y + circle.radius);
        List<BasicPoint> points = new ArrayList<>();
        if (inversionCircle.center.distance(p1) > Config.EPSILON) points.add(p1);
        if (inversionCircle.center.distance(p2) > Config.EPSILON) points.add(p2);
        if (inversionCircle.center.distance(p3) > Config.EPSILON) points.add(p3);
        List<BasicPoint> invertedPoints = new ArrayList<>();
        for (BasicPoint point : points) {
            invertedPoints.add(PointInversionBuilder.getPoint(point, inversionCircle));
        }
        if (invertedPoints.size() < 2)
            return new BasicLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        return LineThroughPointsBuilder.getLine(invertedPoints.get(0), invertedPoints.get(1));
    }

    public static BasicCircle getCircle(BasicCircle circle, BasicCircle inversionCircle) {
        BasicPoint p1 = new BasicPoint(circle.center.x + circle.radius, circle.center.y);
        BasicPoint p2 = new BasicPoint(circle.center.x - circle.radius, circle.center.y);
        BasicPoint p3 = new BasicPoint(circle.center.x, circle.center.y + circle.radius);
        BasicPoint p4 = new BasicPoint(circle.center.x, circle.center.y - circle.radius);
        List<BasicPoint> points = new ArrayList<>();
        if (inversionCircle.center.distance(p1) > Config.EPSILON) points.add(p1);
        if (inversionCircle.center.distance(p2) > Config.EPSILON) points.add(p2);
        if (inversionCircle.center.distance(p3) > Config.EPSILON) points.add(p3);
        if (inversionCircle.center.distance(p4) > Config.EPSILON) points.add(p4);
        List<BasicPoint> invertedPoints = new ArrayList<>();
        for (BasicPoint point : points) {
            invertedPoints.add(PointInversionBuilder.getPoint(point, inversionCircle));
        }
        if (invertedPoints.size() < 3)
            return new BasicCircle(new BasicPoint(), Double.POSITIVE_INFINITY);
        return CircleThroughThreePointsBuilder.getCircle(invertedPoints.get(0), invertedPoints.get(1), invertedPoints.get(2));
    }
}
