package Project.controller.builders.transforms.symmetry.point;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.basicshapes.BasicPoint;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

public class PointReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint point = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (point == null) {
                point = p;
                return true;
            }
            else if (p != point) {
                reflectionPoint = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return point != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        point = null;
        reflectionPoint = null;
    }

    @Override
    public boolean awaitsPoint() {
        return reflectionPoint == null || point == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint reflectedPoint = new GeometricPoint(point, reflectionPoint);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricPoint pPoint = point;
            private final GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                reflectedPoint.isMovable = false;
                setPoint(reflectedPoint, pPoint, pReflectionPoint);
            }
        };
        reflectedPoint.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedPoint, viewablePlane);
    }

    public static void setPoint(GeometricPoint point, BasicPoint pPoint, BasicPoint pReflectionPoint) {
        point.setCoordinates(getPoint(pPoint, pReflectionPoint));
    }

    public static void setPoint(GeometricPoint point, GeometricPoint pPoint, GeometricPoint pReflectionPoint) {
        setPoint(point, pPoint.point, pReflectionPoint.point);
    }

    public static BasicPoint getPoint(BasicPoint point, BasicPoint reflectionPoint) {
        return new BasicPoint(2 * reflectionPoint.x - point.x, 2 * reflectionPoint.y - point.y);
    }
}
