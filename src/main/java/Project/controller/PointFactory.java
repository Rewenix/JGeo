package Project.controller;

import Project.controller.builders.intersections.IntersectionBuilder;
import Project.controller.builders.points.PointBuilder;
import Project.model.geometricshapes.GeometricPoint;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;
import Project.view.viewable.ViewablePoint;
import Project.view.viewable.ViewableShape;

import java.util.ArrayList;
import java.util.List;

public class PointFactory {
    static PointBuilder pointBuilder = new PointBuilder();

    public static ViewableShape createPoint(ViewablePlane viewablePlane, List<ViewableShape> clickedShapesList, double screenX, double screenY) {
        Transformation transformation = viewablePlane.getTransformation();
        List<GeometricShape> clickedShapes = new ArrayList<>();
        for (ViewableShape clickedShape : clickedShapesList) {
            clickedShapes.add(clickedShape.getGeometricShape());
        }

        List<GeometricPoint> intersections = IntersectionBuilder.getIntersections(clickedShapes);
        List<ViewablePoint> viewableIntersections = new ArrayList<>();
        for (GeometricPoint intersection : intersections) {
            viewableIntersections.add(new ViewablePoint(viewablePlane, intersection));
        }
        for (ViewablePoint viewableIntersection : viewableIntersections) {
            if (viewableIntersection.hasPoint(screenX, screenY)) {
                viewablePlane.getPlane().addGeometricShape(viewableIntersection.getGeometricShape());
                viewablePlane.addViewableShape(viewableIntersection);
                return viewableIntersection;
            }
        }
        for (ViewableShape clickedShape : clickedShapesList) {
            if (clickedShape.hasPoint(screenX, screenY)) {
                pointBuilder.acceptArgument(clickedShape.getGeometricShape());
                clickedShape.setOnClicked();
                ViewableShape viewablePoint = pointBuilder.buildPoint(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
                if (viewablePoint == null)
                    viewablePoint = clickedShape;
                pointBuilder.reset();
                viewablePlane.unclickAll();
                return viewablePoint;
            }
        }
        return pointBuilder.buildPoint(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
    }
}
