/**
 * The PerpendicularLineBuilder class is responsible for building a perpendicular line
 * based on a given line and point.
 */

package Project.controller;

import Project.model.*;
import javafx.scene.layout.Pane;

public class PerpendicularLineBuilder implements GeometricShapeBuilder {
    private GeometricLine line;
    private GeometricPoint point;

    @Override
    public Class<?> expectedClass() {
        return line == null ? GeometricLine.class : GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            line = l;
            System.out.println("Accepting line");
        } else if (shape instanceof GeometricPoint p) {
            point = p;
            System.out.println("Accepting point");
        }
    }

    @Override
    public boolean isReady() {
        return line != null && point != null;
    }

    @Override
    public void reset() {
        line = null;
        point = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine perpendicularLine = new GeometricLine("Prosta prostopadła", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                double x1 = pPoint.x;
                double y1 = pPoint.y;
                double x2 = pLine.A;
                double y2 = pLine.B;
                perpendicularLine.A = y2;
                perpendicularLine.B = -x2;
                perpendicularLine.C = -perpendicularLine.A * x1 - perpendicularLine.B * y1;
            }
        };
        perpendicularLine.setUpdater(updater);
        perpendicularLine.update();
        viewPane.getChildren().add(perpendicularLine.getDrawableShape());
        plane.addGeometricShape(perpendicularLine);
    }
}
