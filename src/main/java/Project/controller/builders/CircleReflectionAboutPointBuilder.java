package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class CircleReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                return true;
            }
        }
        else if (shape instanceof GeometricPoint p) {
            if (reflectionPoint == null) {
                reflectionPoint = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return circle != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        circle = null;
        reflectionPoint = null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricCircle reflectedCircle = new GeometricCircle("Odbity okrąg");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle pCircle = circle;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setCircle(reflectedCircle, pCircle, pReflectionPoint);
            }
        };
        reflectedCircle.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedCircle, viewablePlane);
    }

    public static void setCircle(GeometricCircle circle, BasicCircle pCircle, BasicPoint pReflectionPoint) {
        circle.setCoordinates(getCircle(pCircle, pReflectionPoint));
    }

    public static void setCircle(GeometricCircle circle, GeometricCircle pCircle, GeometricPoint pReflectionPoint) {
        setCircle(circle, pCircle.circle, pReflectionPoint.point);
    }

    public static void setCircle(GeometricCircle circle, GeometricPoint pReflectionPoint, GeometricCircle pCircle) {
        setCircle(circle, pCircle.circle, pReflectionPoint.point);
    }

    public static BasicCircle getCircle(BasicCircle circle, BasicPoint reflectionPoint) {
        BasicPoint center = PointReflectionAboutPointBuilder.getPoint(circle.center, reflectionPoint);
        return new BasicCircle(center, circle.radius);
    }
}
