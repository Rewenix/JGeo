package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating a parallel lines.
 */
public class ParallelLineBuilder implements GeometricShapeBuilder {
    private GeometricLine line;
    private GeometricPoint point;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (line == null && shape instanceof GeometricLine l) {
            line = l;
            line.setOnClicked();
            System.out.println("Accepting line");
            return true;
        }
        else if (point == null && shape instanceof GeometricPoint p) {
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
        GeometricLine parallelLine = new GeometricLine("Prosta równoległa", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setLine(parallelLine, pLine, pPoint);
            }
        };
        BuilderUtils.setUpdaterAndAdd(parallelLine, updater, viewPane, plane);
    }

    /**
     * Sets the coefficients of the given parallel line based on the provided
     * values.
     *
     * @param parallelLine The parallel line to set the coefficients for.
     * @param line         The original line.
     * @param point        The point on the parallel line.
     */
    public static void setLine(GeometricLine parallelLine, BasicLine line, BasicPoint point) {
        parallelLine.setCoordinates(getLine(line, point));
    }

    /**
     * Sets the coefficients of the given parallel line based on the coefficients of
     * the original line and the coordinates of a point on it.
     *
     * @param parallelLine The parallel line to set the coefficients for.
     * @param line         The original line.
     * @param point        The point on the parallel line.
     */
    public static void setLine(GeometricLine parallelLine, GeometricLine line, GeometricPoint point) {
        setLine(parallelLine, line.line, point.point);
    }

    public static BasicLine getLine(BasicLine line, BasicPoint point) {
        return new BasicLine(line.A, line.B, -(line.A * point.x + line.B * point.y));
    }
}
