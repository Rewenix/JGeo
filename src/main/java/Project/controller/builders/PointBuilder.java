package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public class PointBuilder implements GeometricShapeBuilder {
    private GeometricShape shape;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        this.shape = shape;
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void reset() {
        shape = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint point = new GeometricPoint("Punkt", plane, transformation, planeX, planeY);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            GeometricShape pShape = shape;

            @Override
            public void update() {
                if (pShape == null) return;
                point.setCoordinates(pShape.projection(point.point));
            }
        };
        BuilderUtils.setUpdaterAndAdd(point, updater, viewPane, plane);
    }
}
