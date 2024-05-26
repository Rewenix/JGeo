package Project.controller;

import Project.Config;
import Project.controller.builders.intersections.IntersectionBuilder;
import Project.controller.builders.points.PointBuilder;
import Project.model.GeometricGenCircle;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.view.ViewablePlane;
import Project.view.ViewablePoint;
import Project.view.ViewableShape;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final Plane2D plane = new Plane2D();
    private final ViewablePlane viewablePlane;
    private final Transformation transformation;
    private Actor selectedActor;
    private final PointBuilder pointBuilder = new PointBuilder();

    public ViewablePlane getViewablePlane() {
        return viewablePlane;
    }

    public Plane2D getPlane() {
        return plane;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Controller(Pane viewPane) {
        viewablePlane = new ViewablePlane(plane, viewPane);
        transformation = viewablePlane.getTransformation();
        selectedActor = null;
    }

    public void changeActor(Actor actor) {
        selectedActor = actor;
        viewablePlane.unclickAll();
    }

    public void handleNormalClick(double screenX, double screenY) {
        if (selectedActor == null) return;
        if (selectedActor instanceof GeometricShapeBuilder selectedBuilder) {
            boolean accepted = false;
            List<ViewableShape> clickedShapesList = viewablePlane.getClickedShapesList(screenX, screenY);
            if (selectedBuilder instanceof PointBuilder) {
                getPoint(clickedShapesList, screenX, screenY);
                return;
            }
            for (ViewableShape clickedShape : clickedShapesList)
                if (selectedBuilder.acceptArgument(clickedShape.getGeometricShape())) {
                    clickedShape.setOnClicked();
                    accepted = true;
                    break;
                }
            if (!accepted && selectedBuilder.awaitsPoint()) {
                GeometricPoint point = getPoint(clickedShapesList, screenX, screenY);
                selectedBuilder.acceptArgument(point);
            }
            if (selectedBuilder.isReady()) {
                selectedBuilder.build(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
                selectedBuilder.reset();
                viewablePlane.unclickAll();
            }
            return;
        }
        if (selectedActor instanceof Shifter selectedShifter) {
            viewablePlane.unclickAll();
            if (viewablePlane.getClickedShape(screenX, screenY) instanceof ViewablePoint point) {
                selectedShifter.setPoint(point.getGeometricShape());
                point.setOnClicked();
                return;
            }
            selectedShifter.setPoint(null);
            selectedShifter.setOrigin(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
            return;
        }
    }

    public void handleDragged(double screenX, double screenY) {
        if (selectedActor == null) return;
        if (selectedActor instanceof Shifter selectedShifter) {
            selectedShifter.shift(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
            double planeX = transformation.toPlaneX(screenX);
            double planeY = transformation.toPlaneY(screenY);
            selectedShifter.setOrigin(planeX, planeY);
            return;
        }
    }

    public void handleScrolled(double screenX, double screenY, double scrollAmount) {
        transformation.changeScale(screenX, screenY, Math.exp(-scrollAmount * Config.ZOOM_SPEED));
        viewablePlane.updateDrawables();
    }

    public void removeLastShape() {
        GeometricShape removedShape = plane.removeLastShape();
        if (removedShape != null) {
            viewablePlane.removeLastShape();
            if (removedShape instanceof GeometricGenCircle)
                viewablePlane.removeLastShape();
        }
    }

    public void clearShapes() {
        plane.clear();
        viewablePlane.clear();
    }

    private GeometricPoint getPoint(List<ViewableShape> clickedShapesList, double screenX, double screenY) {
        GeometricPoint point = null;
        List<GeometricShape> clickedShapes = new ArrayList<>();
        for (ViewableShape clickedShape : clickedShapesList)
            clickedShapes.add(clickedShape.getGeometricShape());

        List<GeometricPoint> intersections = IntersectionBuilder.getIntersections(clickedShapes);
        List<ViewablePoint> viewableIntersections = new ArrayList<>();
        for (GeometricPoint intersection : intersections) {
            viewableIntersections.add(new ViewablePoint("", viewablePlane, intersection));
        }
        for (ViewablePoint viewableIntersection : viewableIntersections) {
            if (viewableIntersection.hasPoint(screenX, screenY)) {
                viewablePlane.getPlane().addGeometricShape(viewableIntersection.getGeometricShape());
                viewablePlane.addViewableShape(viewableIntersection);
                point = viewableIntersection.getGeometricShape();
                return point;
            }
        }
        for (ViewableShape clickedShape : clickedShapesList) {
            if (clickedShape.hasPoint(screenX, screenY)) {
                pointBuilder.acceptArgument(clickedShape.getGeometricShape());
                clickedShape.setOnClicked();
                point = pointBuilder.buildPoint(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
                pointBuilder.reset();
                viewablePlane.unclickAll();
                return point;
            }
        }
        return pointBuilder.buildPoint(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
    }
}
