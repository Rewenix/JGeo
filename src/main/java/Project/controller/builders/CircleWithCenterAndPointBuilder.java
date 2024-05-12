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
            } else if (p != center) {
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
        circle.setUpdater(updater);
        circle.update();
        circle.setViewPane(viewPane);
        plane.addGeometricShape(circle);
    }

    /**
     * Sets the properties of the given circle based on the provided coordinates.
     *
     * @param circle  The circle to set the properties for.
     * @param centerX The x-coordinate of the center point.
     * @param centerY The y-coordinate of the center point.
     * @param pointX  The x-coordinate of the point on the circumference.
     * @param pointY  The y-coordinate of the point on the circumference.
     */
    public static void setCircle(GeometricCircle circle, double centerX, double centerY, double pointX, double pointY) {
        circle.centerX = centerX;
        circle.centerY = centerY;
        circle.R = GeometricPoint.distance(centerX, centerY, pointX, pointY);
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
        setCircle(circle, center.x, center.y, point.x, point.y);
    }
}
