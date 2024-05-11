package Project.controller;

import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.model.GeometricPoint;
import javafx.scene.layout.Pane;

public class Controller {
    private final Plane2D plane = new Plane2D();
    private final Transformation transformation = new Transformation();
    private Actor selectedActor;
    private final Pane viewPane;

    public Plane2D getPlane() {
        return plane;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Controller(Pane viewPane) {
        this.viewPane = viewPane;
        selectedActor = null;
    }

    public void changeActor(Actor actor) {
        System.out.println("Change actor");
        selectedActor = actor;
        plane.unclickAll();
    }

    public void handleNormalClick(double screenX, double screenY) {
        if (selectedActor == null)
            return;
        if (selectedActor instanceof GeometricShapeBuilder selectedBuilder) {
            double planeX = transformation.toPlaneX(screenX);
            double planeY = transformation.toPlaneY(screenY);
            GeometricShape clickedShape = plane.getClickedShape(planeX, planeY, selectedBuilder.expectedClass());
            selectedBuilder.acceptArgument(clickedShape);
            if (selectedBuilder.isReady()) {
                System.out.println("Building shape with builder");
                selectedBuilder.build(plane, transformation, viewPane, planeX, planeY);
                selectedBuilder.reset();
                plane.unclickAll();
            }
            return;
        }
        if (selectedActor instanceof Shifter selectedShifter) {
            plane.unclickAll();
            double planeX = transformation.toPlaneX(screenX);
            double planeY = transformation.toPlaneY(screenY);
            if (plane.getClickedShape(planeX, planeY) instanceof GeometricPoint point && point != null) {
                selectedShifter.setPoint(point);
                return;
            }
            selectedShifter.setPoint(null);
            selectedShifter.setOrigin(planeX, planeY);
            return;
        }
    }

    public void handleDragged(double screenX, double screenY) {
        if (selectedActor == null)
            return;
        if (selectedActor instanceof Shifter selectedShifter) {
            selectedShifter.shift(transformation.toPlaneX(screenX), transformation.toPlaneY(screenY));
            double planeX = transformation.toPlaneX(screenX);
            double planeY = transformation.toPlaneY(screenY);
            selectedShifter.setOrigin(planeX, planeY);
            return;
        }
    }

    public void removeLastShape() {
        plane.removeLastShape();
    }

    public void clearShapes() {
        plane.clear();
    }
}
