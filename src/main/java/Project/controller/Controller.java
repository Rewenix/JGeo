package Project.controller;

import Project.Config;
import Project.controller.builders.points.PointBuilder;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.model.geometricshapes.GeometricGenCircle;
import Project.view.viewable.ViewablePlane;
import javafx.scene.layout.Pane;

public class Controller {
    private final Plane2D plane = new Plane2D();
    private final ViewablePlane viewablePlane;
    private final Transformation transformation;
    private Actor selectedActor;

    public ViewablePlane getViewablePlane() {
        return viewablePlane;
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

    public void handleClick(double screenX, double screenY) {
        if (selectedActor != null) selectedActor.handleClick(viewablePlane, screenX, screenY);
    }

    public void handleDragged(double screenX, double screenY) {
        if (selectedActor instanceof Shifter selectedShifter) selectedShifter.handleDragged(screenX, screenY);
    }

    public void handleScrolled(double screenX, double screenY, double scrollAmount) {
        transformation.changeScale(screenX, screenY, Math.exp(-scrollAmount * Config.ZOOM_SPEED));
        viewablePlane.updateDrawables();
    }

    public void removeLastShape() {
        if (selectedActor instanceof GeometricShapeBuilder selectedBuilder) selectedBuilder.reset();
        GeometricShape removedShape = plane.removeLastShape();
        if (removedShape != null) {
            viewablePlane.removeLastShape();
            if (removedShape instanceof GeometricGenCircle)
                viewablePlane.removeLastShape();
        }
    }

    public void clearShapes() {
        if (selectedActor instanceof GeometricShapeBuilder selectedBuilder) selectedBuilder.reset();
        plane.clear();
        viewablePlane.clear();
    }
}
