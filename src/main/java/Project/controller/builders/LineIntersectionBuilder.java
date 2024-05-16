package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class LineIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricLine a = null;
    private GeometricLine b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (a == null) {
                a = l;
                a.setOnClicked();
                System.out.println("Accepting line");
                return true;
            } else if (l != a) {
                b = l;
                b.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        return false;
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
        GeometricPoint intersection = new GeometricPoint("Przecięcie", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine lA = a;
            private GeometricLine lB = b;

            @Override
            public void update() {
                setPoint(intersection, lA, lB);
            }
        };
        intersection.setUpdater(updater);
        intersection.update();
        intersection.setViewPane(viewPane);
        plane.addGeometricShape(intersection);
    }

    public static void setPoint(GeometricPoint point, double a1, double b1, double c1, double a2, double b2, double c2) {
        point.x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1);
        point.y = (c1 * a2 - c2 * a1) / (a1 * b2 - a2 * b1);
    }

    public static void setPoint(GeometricPoint point, GeometricLine lA, GeometricLine lB) {
        setPoint(point, lA.A, lA.B, lA.C, lB.A, lB.B, lB.C);
    }
}
