package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricCircle;
import Project.model.*;
import javafx.scene.layout.Pane;
import Project.controller.Transformation;

public class CircleCenterBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            circle = c;
            return true;
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return circle != null;
    }

    @Override
    public void reset() {
        circle = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint center = new GeometricPoint("Środek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setPoint(center, c.centerX, c.centerY);
            }
        };
        center.setUpdater(updater);
        center.update();
        center.setViewPane(viewPane);
        plane.addGeometricShape(center);
    }

    public static void setPoint(GeometricPoint point, double centerX, double centerY) {
        point.x = centerX;
        point.y = centerY;
    }

    public static void setPoint(GeometricPoint point, GeometricCircle circle) {
        setPoint(point, circle.centerX, circle.centerY);
    }
}
