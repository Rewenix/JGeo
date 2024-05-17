package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class CircleReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                circle.setOnClicked();
                System.out.println("Accepting circle");
                return true;
            }
        }
        else if (shape instanceof GeometricPoint p) {
            if (reflectionPoint == null) {
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
        return circle != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        circle = null;
        reflectionPoint = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricCircle reflectedCircle = new GeometricCircle("Odbity okrąg", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle pCircle = circle;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setCircle(reflectedCircle, pCircle, pReflectionPoint);
            }
        };
        reflectedCircle.setUpdater(updater);
        reflectedCircle.update();
        reflectedCircle.setViewPane(viewPane);
        plane.addGeometricShape(reflectedCircle);
    }

    public static void setCircle(GeometricCircle circle, double centerX, double centerY, double r, double reflectionX, double reflectionY) {
        circle.centerX = 2 * reflectionX - centerX;
        circle.centerY = 2 * reflectionY - centerY;
        circle.R = r;
    }

    public static void setCircle(GeometricCircle circle, GeometricCircle pCircle, GeometricPoint pReflectionPoint) {
        setCircle(circle, pCircle.centerX, pCircle.centerY, pCircle.R, pReflectionPoint.x, pReflectionPoint.y);
    }

    public static void setCircle(GeometricCircle circle, GeometricPoint pReflectionPoint, GeometricCircle pCircle) {
        setCircle(circle, pCircle.centerX, pCircle.centerY, pCircle.R, pReflectionPoint.x, pReflectionPoint.y);
    }
}
