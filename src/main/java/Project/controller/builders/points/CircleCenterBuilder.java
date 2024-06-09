package Project.controller.builders.points;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.basicshapes.BasicCircle;
import Project.model.geometricshapes.GeometricCircle;
import Project.model.geometricshapes.GeometricGenCircle;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

public class CircleCenterBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            circle = c;
            return true;
        }
        if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            circle = cc;
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
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint center = new GeometricPoint(circle);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricCircle c = circle;

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
