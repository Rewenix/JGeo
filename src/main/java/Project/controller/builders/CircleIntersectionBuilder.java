package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

import java.util.List;

public class CircleIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricCircle a = null;
    private GeometricCircle b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (a == null) {
                a = c;
                a.setOnClicked();
                System.out.println("Accepting circle");
                return true;
            }
            else if (c != a) {
                b = c;
                b.setOnClicked();
                System.out.println("Accepting circle");
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint i1 = new GeometricPoint("Intersection 1", plane, transformation);
        GeometricPoint i2 = new GeometricPoint("Intersection 2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle cA = a;
            private GeometricCircle cB = b;

            @Override
            public void update() {
                setPoints(i1, i2, cA, cB);
            }
        };
        BuilderUtils.setUpdaterAndAdd(i1, updater, viewPane, plane);
        BuilderUtils.setUpdaterAndAdd(i2, updater, viewPane, plane);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, BasicCircle c1, BasicCircle c2) {
        List<BasicPoint> points = getPoints(c1, c2);
        i1.setCoordinates(points.get(0));
        i2.setCoordinates(points.get(1));
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle c1, GeometricCircle c2) {
        setPoints(i1, i2, c1.circle, c2.circle);
    }

    public static List<BasicPoint> getPoints(BasicCircle c1, BasicCircle c2) {
        double d = BasicPoint.distance(c1.center, c2.center);
        double r1 = c1.radius;
        double r2 = c2.radius;
        double x1 = c1.center.x;
        double y1 = c1.center.y;
        double x2 = c2.center.x;
        double y2 = c2.center.y;

        double aa = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);
        double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(aa, 2));
        double x3 = x1 + aa * (x2 - x1) / d;
        double y3 = y1 + aa * (y2 - y1) / d;
        BasicPoint i1 = new BasicPoint(x3 + h * (y2 - y1) / d, y3 - h * (x2 - x1) / d);
        BasicPoint i2 = new BasicPoint(x3 - h * (y2 - y1) / d, y3 + h * (x2 - x1) / d);
        return List.of(i1, i2);
    }
}
