package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating perpendicular lines.
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
        GeometricLine perpendicularLine = new GeometricLine("Prosta prostopadła", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setLine(perpendicularLine, pLine, pPoint);
            }
        };
        BuilderUtils.setUpdaterAndAdd(perpendicularLine, updater, viewPane, plane);
    }


    public static void setLine(GeometricLine perpendicularLine, GeometricLine line, GeometricPoint point) {
        setLine(perpendicularLine, line.line, point.point);
    }

    public static void setLine(GeometricLine perpendicularLine, GeometricPoint point, GeometricLine line) {
        setLine(perpendicularLine, line.line, point.point);
    }

    public static void setLine(GeometricLine perpendicularLine, BasicLine line, BasicPoint point) {
        perpendicularLine.setCoordinates(getLine(line, point));
    }

    public static BasicLine getLine(BasicLine line, BasicPoint point) {
        return new BasicLine(line.B, -line.A, -line.B * point.x + line.A * point.y);
    }
}
