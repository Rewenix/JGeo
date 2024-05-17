package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public class PointReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint point = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (point == null) {
                point = p;
                point.setOnClicked();
                System.out.println("Accepting point");
                return true;
            }
            else if (p != point) {
                reflectionPoint = p;
                reflectionPoint.setOnClicked();
                System.out.println("Accepting point");
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint reflectedPoint = new GeometricPoint("Punkt odbity", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setPoint(reflectedPoint, pPoint, pReflectionPoint);
            }
        };
        reflectedPoint.setUpdater(updater);
        reflectedPoint.update();
        reflectedPoint.setViewPane(viewPane);
        plane.addGeometricShape(reflectedPoint);
    }

    public static void setPoint(GeometricPoint point, double x, double y, double reflectionX, double reflectionY) {
        point.x = 2 * reflectionX - x;
        point.y = 2 * reflectionY - y;
    }

    public static void setPoint(GeometricPoint point, GeometricPoint pPoint, GeometricPoint pReflectionPoint) {
        setPoint(point, pPoint.x, pPoint.y, pReflectionPoint.x, pReflectionPoint.y);
    }
}
