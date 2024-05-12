package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating a free point.
 */
public class FreePointBuilder implements GeometricShapeBuilder {

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint point = new GeometricPoint("Punkt", plane, transformation, planeX, planeY);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {

            @Override
            public void update() {

            }
        };
        point.setUpdater(updater);
        point.setViewPane(viewPane);
        plane.addGeometricShape(point);
    }
}
