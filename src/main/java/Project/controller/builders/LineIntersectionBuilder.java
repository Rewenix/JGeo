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
            }
            else if (l != a) {
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
        BuilderUtils.setUpdaterAndAdd(intersection, updater, viewPane, plane);
    }

    public static void setPoint(GeometricPoint point, BasicLine lA, BasicLine lB) {
        point.setCoordinates(getPoint(lA, lB));
    }

    public static void setPoint(GeometricPoint point, GeometricLine lA, GeometricLine lB) {
        setPoint(point, lA.line, lB.line);
    }

    public static BasicPoint getPoint(BasicLine lA, BasicLine lB) {
        return new BasicPoint((lA.B * lB.C - lB.B * lA.C) / (lA.A * lB.B - lB.A * lA.B),
                (lA.C * lB.A - lB.C * lA.A) / (lA.A * lB.B - lB.A * lA.B));
    }
}
