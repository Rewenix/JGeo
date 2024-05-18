package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

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
                setPoint(center, c);
            }
        };
        BuilderUtils.setUpdaterAndAdd(center, updater, viewPane, plane);
    }

    public static void setPoint(GeometricPoint point, BasicCircle circle) {
        point.setCoordinates(circle.center);
    }

    public static void setPoint(GeometricPoint point, GeometricCircle circle) {
        setPoint(point, circle.circle);
    }
}
