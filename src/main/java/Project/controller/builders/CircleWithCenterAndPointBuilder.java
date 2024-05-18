package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating a circle with a center point and another point
 * on the circumference.
 */
public class CircleWithCenterAndPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint center = null;
    private GeometricPoint point = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (center == null) {
                center = p;
                center.setOnClicked();
                System.out.println("Accepting point");
                return true;
            }
            else if (p != center) {
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
        BuilderUtils.setUpdaterAndAdd(circle, updater, viewPane, plane);
    }

    public static void setCircle(GeometricCircle circle, BasicPoint center, BasicPoint point) {
        circle.setCoordinates(getCircle(center, point));
    }

    /**
     * Sets the properties of the given circle based on the provided center and
     * point.
     *
     * @param circle The circle to set the properties for.
     * @param center The center point of the circle.
     * @param point  The point on the circumference of the circle.
     */
    public static void setCircle(GeometricCircle circle, GeometricPoint center, GeometricPoint point) {
        setCircle(circle, center.point, point.point);
    }

    public static BasicCircle getCircle(BasicPoint center, BasicPoint point) {
        double R = BasicPoint.distance(center, point);
        return new BasicCircle(center, R);
    }
}
