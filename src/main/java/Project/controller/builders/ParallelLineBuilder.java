/**
 * The ParallelLineBuilder class is responsible for building a parallel line
 * based on a given line and point.
 */

package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class ParallelLineBuilder implements GeometricShapeBuilder {
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
        GeometricLine parallelLine = new GeometricLine("Prosta równoległa", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                setLine(parallelLine, pLine, pPoint);
            }
        };
        parallelLine.setUpdater(updater);
        parallelLine.update();
        parallelLine.setViewPane(viewPane);
        plane.addGeometricShape(parallelLine);
    }

    public static void setLine(GeometricLine parallelLine, double lineA, double lineB, double pointX, double pointY) {
        parallelLine.A = lineA;
        parallelLine.B = lineB;
        parallelLine.C = -(lineA * pointX + lineB * pointY);
    }

    public static void setLine(GeometricLine parallelLine, GeometricLine line, GeometricPoint point) {
        setLine(parallelLine, line.A, line.B, point.x, point.y);
    }
}
