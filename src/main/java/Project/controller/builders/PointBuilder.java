package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.view.ViewablePlane;

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
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricPoint point = new GeometricPoint("", planeX, planeY);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            GeometricShape pShape = shape;

            @Override
            public void update() {
                if (pShape == null) return;
                point.setCoordinates(pShape.projection(point.point));
            }
        };
        point.setUpdater(updater);
        BuilderUtils.addToPlane(point, viewablePlane);
    }
}
