package Project.controller.builders.transforms.symmetry.point;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.basicshapes.BasicCircle;
import Project.model.basicshapes.BasicPoint;
import Project.model.geometricshapes.GeometricCircle;
import Project.model.geometricshapes.GeometricGenCircle;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

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
        else if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            if (circle == null) {
                circle = cc;
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
    public boolean awaitsPoint() {
        return reflectionPoint == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricCircle reflectedCircle = new GeometricCircle(circle, reflectionPoint);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricCircle pCircle = circle;
            private final GeometricPoint pReflectionPoint = reflectionPoint;

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
