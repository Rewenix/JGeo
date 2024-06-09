package Project.controller;

import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;
import Project.view.viewable.ViewableShape;

import java.util.Set;

public class GeometricShapeRemover implements Actor {
    @Override
    public void handleClick(ViewablePlane viewablePlane, double screenX, double screenY) {
        ViewableShape clickedShape = viewablePlane.getClickedShape(screenX, screenY);
        Set<GeometricShape> dropped = viewablePlane.getPlane().removeShape(clickedShape.getGeometricShape());
        viewablePlane.removeShapes(dropped);
    }
}
