package Project.controller.builders.points;

import Project.controller.GeometricShapeBuilder;
import Project.controller.PointFactory;
import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;
import Project.view.viewable.ViewableShape;

import java.util.List;

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
    public boolean awaitsPoint() {
        return shape == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        buildPoint(viewablePlane, planeX, planeY);
    }

    public ViewableShape buildPoint(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (shape instanceof GeometricPoint point) {
            return null;
        }
        GeometricPoint point;
        if (shape == null)
            point = new GeometricPoint(planeX, planeY);
        else
            point = new GeometricPoint(planeX, planeY, shape);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            final GeometricShape pShape = shape;

            @Override
            public void update() {
                if (pShape != null) point.setCoordinates(pShape.projection(point.point));
            }
        };
        point.setUpdater(updater);
        ViewableShape viewablePoint = BuilderUtils.addToPlane(point, viewablePlane);
        reset();
        return viewablePoint;
    }

    @Override
    public void handleClick(ViewablePlane viewablePlane, double screenX, double screenY) {
        List<ViewableShape> clickedShapesList = viewablePlane.getClickedShapesList(screenX, screenY);
        PointFactory.createPoint(viewablePlane, clickedShapesList, screenX, screenY);
    }
}
