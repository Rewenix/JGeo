package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

import java.util.List;

public class LineAndCircleIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                circle.setOnClicked();
                System.out.println("Accepting circle");
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint intersection1 = new GeometricPoint("Intersection 1", plane, transformation);
        GeometricPoint intersection2 = new GeometricPoint("Intersection 2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine l = line;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setPoints(intersection1, intersection2, l, c);
            }
        };
        BuilderUtils.setUpdaterAndAdd(intersection1, updater, viewPane, plane);
        BuilderUtils.setUpdaterAndAdd(intersection2, updater, viewPane, plane);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, BasicLine l, BasicCircle c) {
        List<BasicPoint> points = getPoints(l, c);
        i1.setCoordinates(points.get(0));
        i2.setCoordinates(points.get(1));
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricLine l, GeometricCircle c) {
        setPoints(i1, i2, l.line, c.circle);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle c, GeometricLine l) {
        setPoints(i1, i2, l.line, c.circle);
    }

    public static List<BasicPoint> getPoints(BasicLine l, BasicCircle circle) {
        double A = l.A;
        double B = l.B;
        double x0 = circle.center.x;
        double y0 = circle.center.y;
        double R = circle.radius;
        if (Math.abs(l.B) <= BuilderUtils.EPSILON) {
            A = l.B;
            B = l.A;
            x0 = circle.center.y;
            y0 = circle.center.x;
        }

        double a = A * A + B * B;
        double b = 2 * (A * l.C + A * B * y0 - B * B * x0);
        double c = l.C * l.C + 2 * B * l.C * y0 - B * B * (R * R - x0 * x0 - y0 * y0);
        double delta = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
        double y1 = (-A * x1 - l.C) / B;
        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
        double y2 = (-A * x2 - l.C) / B;

        if (Math.abs(l.B) <= BuilderUtils.EPSILON) {
            double tmp = x1;
            x1 = y1;
            y1 = tmp;
            tmp = x2;
            x2 = y2;
            y2 = tmp;
        }

        if (B < 0) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        return List.of(new BasicPoint(x1, y1), new BasicPoint(x2, y2));
    }
}