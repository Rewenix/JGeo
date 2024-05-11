package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class PerpendicularBisectorBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b;

    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                a.setOnClicked();
                System.out.println("Accepting point");
            } else if (p != a) {
                b = p;
                b.setOnClicked();
                System.out.println("Accepting point");
            }
        }
    }

    @Override
    public boolean isReady() {
        return a != null && b != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Symetralna", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                setLine(line, pA.x, pA.y, pB.x, pB.y);
            }
        };
        line.setUpdater(updater);
        line.update();
        line.setViewPane(viewPane);
        plane.addGeometricShape(line);
    }

    public static void setLine(GeometricLine line, double point1X, double point1Y, double point2X, double point2Y) {
        double midX = (point1X + point2X) / 2;
        double midY = (point1Y + point2Y) / 2;

        double a = point1Y - point2Y;
        double b = point2X - point1X;

        PerpendicularLineBuilder.setLine(line, a, b, midX, midY);
    }

    public static void setLine(GeometricLine line, GeometricPoint point1, GeometricPoint point2) {
        setLine(line, point1.x, point1.y, point2.x, point2.y);
    }
}
