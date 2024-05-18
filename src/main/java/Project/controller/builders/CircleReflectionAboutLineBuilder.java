package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class CircleReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;
    private GeometricLine line = null;

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
        else if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return circle != null && line != null;
    }

    @Override
    public void reset() {
        circle = null;
        line = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricCircle reflectedCircle = new GeometricCircle("Odbite kolo", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle pCircle = circle;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setCircle(reflectedCircle, pCircle, pLine);
            }
        };
        reflectedCircle.setUpdater(updater);
        reflectedCircle.update();
        reflectedCircle.setViewPane(viewPane);
        plane.addGeometricShape(reflectedCircle);
    }

    public static void setCircle(GeometricCircle reflectedCircle, BasicCircle pCircle, BasicLine reflectionLine) {
        reflectedCircle.setCoordinates(getCircle(pCircle, reflectionLine));
    }

    public static void setCircle(GeometricCircle reflectedCircle, GeometricCircle pCircle, GeometricLine pLine) {
        setCircle(reflectedCircle, pCircle.circle, pLine.line);
    }

    public static BasicCircle getCircle(BasicCircle pCircle, BasicLine pLine) {
        BasicPoint center = PointReflectionAboutLineBuilder.getPoint(pCircle.center, pLine);
        return new BasicCircle(center, pCircle.radius);
    }
}
