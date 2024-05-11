/**
 * The PerpendicularLineBuilder class is responsible for building a perpendicular line
 * based on a given line and point.
 */

package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
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
            line.setOnClicked();
            System.out.println("Accepting line");
        } else if (shape instanceof GeometricPoint p) {
            point = p;
            point.setOnClicked();
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
                setLine(perpendicularLine, pLine, pPoint);
            }
        };
        perpendicularLine.setUpdater(updater);
        perpendicularLine.update();
        perpendicularLine.setViewPane(viewPane);
        plane.addGeometricShape(perpendicularLine);
    }

    public static void setLine(GeometricLine perpendicularLine, double lineA, double lineB, double pointX,
            double pointY) {
        perpendicularLine.A = lineB;
        perpendicularLine.B = -lineA;
        perpendicularLine.C = -perpendicularLine.A * pointX - perpendicularLine.B * pointY;
    }

    public static void setLine(GeometricLine perpendicularLine, GeometricLine line, GeometricPoint point) {
        setLine(perpendicularLine, line.A, line.B, point.x, point.y);
    }

    public static void setLine(GeometricLine perpendicularLine, GeometricPoint point, GeometricLine line) {
        setLine(perpendicularLine, line, point);
    }
}
