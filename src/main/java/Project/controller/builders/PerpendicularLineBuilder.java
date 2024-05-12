package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating perpendicular lines in a geometric plane.
 */
public class PerpendicularLineBuilder implements GeometricShapeBuilder {
    private GeometricLine line;
    private GeometricPoint point;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (line == null && shape instanceof GeometricLine l) {
            line = l;
            line.setOnClicked();
            System.out.println("Accepting line");
            return true;
        } else if (point == null && shape instanceof GeometricPoint p) {
            point = p;
            point.setOnClicked();
            System.out.println("Accepting point");
            return true;
        }
        return false;
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

    /**
     * Sets the coefficients of a geometric line to represent a perpendicular line
     * to the given line passing through the given point.
     *
     * @param perpendicularLine The geometric line to set the coefficients for.
     * @param line              The original line.
     * @param point             The point through which the perpendicular line
     *                          passes.
     */
    public static void setLine(GeometricLine perpendicularLine, GeometricLine line, GeometricPoint point) {
        setLine(perpendicularLine, line.A, line.B, point.x, point.y);
    }

    /**
     * Sets the coefficients of a geometric line to represent a perpendicular line
     * to the given line passing through the given point.
     *
     * @param perpendicularLine The geometric line to set the coefficients for.
     * @param point             The point through which the perpendicular line
     *                          passes.
     * @param line              The original line.
     */
    public static void setLine(GeometricLine perpendicularLine, GeometricPoint point, GeometricLine line) {
        setLine(perpendicularLine, line, point);
    }

    /**
     * Sets the coefficients of a geometric line to represent a perpendicular line
     * to the given line passing through the given point.
     *
     * @param perpendicularLine The geometric line to set the coefficients for.
     * @param lineA             The coefficient A of the original line.
     * @param lineB             The coefficient B of the original line.
     * @param pointX            The x-coordinate of the point through which the
     *                          perpendicular line passes.
     * @param pointY            The y-coordinate of the point through which the
     *                          perpendicular line passes.
     */
    public static void setLine(GeometricLine perpendicularLine, double lineA, double lineB, double pointX,
            double pointY) {
        perpendicularLine.A = lineB;
        perpendicularLine.B = -lineA;
        perpendicularLine.C = -perpendicularLine.A * pointX - perpendicularLine.B * pointY;
    }
}
