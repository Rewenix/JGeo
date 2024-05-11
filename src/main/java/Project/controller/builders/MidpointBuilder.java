package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class MidpointBuilder implements GeometricShapeBuilder {
    private GeometricPoint a = null;
    private GeometricPoint b = null;

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
        GeometricPoint midpoint = new GeometricPoint("Środek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                midpoint.x = (pA.x + pB.x) / 2;
                midpoint.y = (pA.y + pB.y) / 2;
            }
        };
        midpoint.setUpdater(updater);
        midpoint.update();
        midpoint.setViewPane(viewPane);
        plane.addGeometricShape(midpoint);
    }
}
