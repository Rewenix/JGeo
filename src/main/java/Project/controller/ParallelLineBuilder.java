/**
 * The ParallelLineBuilder class is responsible for building a parallel line
 * based on a given line and point.
 */

package Project.controller;

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
        GeometricLine parallelLine = new GeometricLine("Prosta równoległa", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                double x1 = pPoint.x;
                double y1 = pPoint.y;
                parallelLine.A = pLine.A;
                parallelLine.B = pLine.B;
                parallelLine.C = -(pLine.A * x1 + pLine.B * y1);
            }
        };
        parallelLine.setUpdater(updater);
        parallelLine.update();
        viewPane.getChildren().add(parallelLine.getDrawableShape());
        plane.addGeometricShape(parallelLine);
    }
}
