package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class CircleWithCenterAndPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint center = null;
    private GeometricPoint point = null;

    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (center == null) {
                center = p;
                center.setOnClicked();
                System.out.println("Accepting point");
                return true;
            } else if (p != center) {
                point = p;
                point.setOnClicked();
                System.out.println("Accepting point");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return center != null && point != null;
    }

    @Override
    public void reset() {
        center = null;
        point = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricCircle circle = new GeometricCircle("Okrąg", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pCenter = center;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setCircle(circle, pCenter, pPoint);
            }
        };
        circle.setUpdater(updater);
        circle.update();
        circle.setViewPane(viewPane);
        plane.addGeometricShape(circle);
    }

    public static void setCircle(GeometricCircle circle, double centerX, double centerY, double pointX, double pointY) {
        circle.centerX = centerX;
        circle.centerY = centerY;
        circle.R = GeometricPoint.distance(centerX, centerY, pointX, pointY);
    }

    public static void setCircle(GeometricCircle circle, GeometricPoint center, GeometricPoint point) {
        setCircle(circle, center.x, center.y, point.x, point.y);
    }
}
