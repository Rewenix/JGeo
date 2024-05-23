package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

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
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint center = new GeometricPoint("Środek");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setPoint(center, c);
            }
        };
        center.setUpdater(updater);
        BuilderUtils.addToPlane(center, viewablePlane);
    }

    public static void setPoint(GeometricPoint point, BasicCircle circle) {
        point.setCoordinates(circle.center);
    }

    public static void setPoint(GeometricPoint point, GeometricCircle circle) {
        setPoint(point, circle.circle);
    }
}
