package Project.controller.builders;

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
        GeometricLine line = new GeometricLine("Symetralna", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                double midX = (pA.x + pB.x) / 2;
                double midY = (pA.y + pB.y) / 2;

                double a = pA.y - pB.y;
                double b = pB.x - pA.x;

                PerpendicularLineBuilder.setLine(line, a, b, midX, midY);
            }
        };
        line.setUpdater(updater);
        line.update();
        viewPane.getChildren().add(line.getDrawableShape());
        plane.addGeometricShape(line);
    }
}
