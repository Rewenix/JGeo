package Project.controller;

import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.view.ViewablePlane;
import Project.view.ViewablePoint;
import Project.view.ViewableShape;
import javafx.scene.layout.Pane;

import java.util.List;

public class Controller {
    private final Plane2D plane = new Plane2D();
    private final ViewablePlane viewablePlane;
    private final Transformation transformation;
    private Actor selectedActor;
    // private final Pane viewPane;

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
        // this.viewPane = viewPane;
        viewablePlane = new ViewablePlane(plane, viewPane);
        transformation = viewablePlane.getTransformation();
        selectedActor = null;
    }

    public void changeActor(Actor actor) {
        System.out.println("Change actor");
        selectedActor = actor;
        viewablePlane.unclickAll();
    }

    public void handleNormalClick(double screenX, double screenY) {
        if (selectedActor == null) return;
        if (selectedActor instanceof GeometricShapeBuilder selectedBuilder) {
            List<ViewableShape> clickedShapesList = viewablePlane.getClickedShapesList(screenX, screenY);
            for (ViewableShape clickedShape : clickedShapesList)
                if (selectedBuilder.acceptArgument(clickedShape.getGeometricShape())) {
                    clickedShape.setOnClicked();
                    break;
                }
            if (selectedBuilder.isReady()) {
                System.out.println("Building shape with builder");
                selectedBuilder.build(viewablePlane, transformation.toScreenX(screenX), transformation.toScreenY(screenY));
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
            viewablePlane.updateDrawables();
            return;
        }
    }

    public void removeLastShape() {
        viewablePlane.removeLastShape();
    }

    public void clearShapes() {
        viewablePlane.clear();
    }
}
