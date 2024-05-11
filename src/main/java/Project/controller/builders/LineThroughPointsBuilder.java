package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class LineThroughPointsBuilder implements GeometricShapeBuilder {
    protected GeometricPoint a = null;
    protected GeometricPoint b = null;

    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                System.out.println("Accepting point");
            } else if (p != a) {
                b = p;
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
        GeometricLine line = new GeometricLine("Prosta", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                line.setEquation(pA, pB);
            }
        };
        line.setUpdater(updater);
        line.update();
        viewPane.getChildren().add(line.getDrawableShape());
        plane.addGeometricShape(line);
    }

    public static void setLine(GeometricLine line, GeometricPoint a, GeometricPoint b) {
        line.A = a.y - b.y;
        line.B = b.x - a.x;
        line.C = a.x * b.y - b.x * a.y;
    }
}
