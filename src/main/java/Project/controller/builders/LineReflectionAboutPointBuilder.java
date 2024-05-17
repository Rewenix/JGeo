package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class LineReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
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
        return line != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        line = null;
        reflectionPoint = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine reflectedLine = new GeometricLine("Odbita prosta", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setLine(reflectedLine, pLine, pReflectionPoint);
            }
        };
        reflectedLine.setUpdater(updater);
        reflectedLine.update();
        reflectedLine.setViewPane(viewPane);
        plane.addGeometricShape(reflectedLine);
    }

    public static void setLine(GeometricLine line, double a, double b, double c, double reflectionX, double reflectionY) {
        line.A = a;
        line.B = b;
        line.C = -(2 * a * reflectionX + 2 * b * reflectionY + c);
    }

    public static void setLine(GeometricLine line, GeometricLine pLine, GeometricPoint pReflectionPoint) {
        setLine(line, pLine.A, pLine.B, pLine.C, pReflectionPoint.x, pReflectionPoint.y);
    }

    public static void setLine(GeometricLine line, GeometricPoint pReflectionPoint, GeometricLine pLine) {
        setLine(line, pLine.A, pLine.B, pLine.C, pReflectionPoint.x, pReflectionPoint.y);
    }
}
