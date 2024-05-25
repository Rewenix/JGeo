package Project.controller;

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
    private static final double ZOOM_SPEED = 0.002; //TODO: wczytywać z pliku konfiguracyjnego

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
            List<ViewableShape> clickedShapesList = viewablePlane.getClickedShapesList(screenX, screenY);
            if (selectedActor instanceof PointBuilder pointBuilder) {
                List<GeometricShape> clickedShapes = new ArrayList<>();
                for (ViewableShape clickedShape : clickedShapesList)
                    clickedShapes.add(clickedShape.getGeometricShape());

                List<GeometricPoint> intersections = IntersectionBuilder.getIntersections(clickedShapes);
                List<ViewableShape> viewableIntersections = new ArrayList<>();
                for (GeometricPoint intersection : intersections) {
                    viewableIntersections.add(new ViewablePoint("", viewablePlane, intersection));
                }
                for (ViewableShape viewableIntersection : viewableIntersections) {
                    if (viewableIntersection.hasPoint(screenX, screenY)) {
                        pointBuilder.acceptArgument(viewableIntersection.getGeometricShape());
                        viewableIntersection.setOnClicked();
                        break;
                    }
                }
                if (pointBuilder.isReady()) {
                    pointBuilder.build(viewablePlane, transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
                    pointBuilder.reset();
                    viewablePlane.unclickAll();
                    return;
                }
            }
            for (ViewableShape clickedShape : clickedShapesList)
                if (selectedBuilder.acceptArgument(clickedShape.getGeometricShape())) {
                    clickedShape.setOnClicked();
                    break;
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
        transformation.changeScale(screenX, screenY, Math.exp(-scrollAmount * ZOOM_SPEED));
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
}
