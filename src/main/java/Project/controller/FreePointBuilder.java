package Project.controller;

import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import Project.model.Point;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class FreePointBuilder implements GeometricShapeBuilder{
    @Override
    public Class<?> expectedClass() {
        return Point.class;
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
        Point point = new Point("Punkt", plane, transformation, planeX, planeY);
        GeometricShapeUpdater updater = new GeometricShapeUpdater(){

            @Override
            public void update() {

            }
        };
        point.setUpdater(updater);
        viewPane.getChildren().add(point.getDrawableShape());
        plane.addGeometricShape(point);
    }
}
