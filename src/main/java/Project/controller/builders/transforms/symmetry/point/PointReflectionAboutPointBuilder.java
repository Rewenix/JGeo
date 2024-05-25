package Project.controller.builders.transforms.symmetry.point;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.BasicPoint;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.view.ViewablePlane;

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
        GeometricPoint reflectedPoint = new GeometricPoint("Punkt odbity");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
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
