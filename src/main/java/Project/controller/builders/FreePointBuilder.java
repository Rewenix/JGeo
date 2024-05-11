package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public class FreePointBuilder implements GeometricShapeBuilder {
    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {

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
        viewPane.getChildren().addAll(point.getDrawableShape(), point.getDrawableHub());
        point.getDrawableHub().toBack();
        plane.addGeometricShape(point);
    }
}