package Project.controller;

import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;
import Project.view.viewable.ViewableShape;

import java.util.List;

public interface GeometricShapeBuilder extends Actor {

    boolean acceptArgument(GeometricShape shape);

    boolean isReady();

    void build(ViewablePlane viewablePlane, double planeX, double planeY);

    boolean awaitsPoint();

    void reset();

    @Override
    default void handleClick(ViewablePlane viewablePlane, double screenX, double screenY) {
        Transformation transformation = viewablePlane.getTransformation();
        boolean accepted = false;
        List<ViewableShape> clickedShapesList = viewablePlane.getClickedShapesList(screenX, screenY);
        for (ViewableShape clickedShape : clickedShapesList) {
            if (acceptArgument(clickedShape.getGeometricShape())) {
                clickedShape.setOnClicked();
                accepted = true;
                break;
            }
        }
        if (!accepted && awaitsPoint()) {
            ViewableShape viewablePoint = PointFactory.createPoint(viewablePlane, clickedShapesList, screenX, screenY);
            if (acceptArgument(viewablePoint.getGeometricShape()))
                viewablePoint.setOnClicked();
        }
        if (isReady()) {
            build(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
            reset();
            viewablePlane.unclickAll();
        }
    }
}