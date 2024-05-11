package Project.controller;

import Project.controller.builders.FreePointBuilder;
import Project.controller.builders.GeometricShapeBuilder;
import Project.controller.builders.LineThroughPointsBuilder;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.model.GeometricPoint;
import javafx.scene.layout.Pane;

public class BasicController {
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

    public BasicController(Pane viewPane) {
        this.viewPane = viewPane;
        selectedActor = null;
    }

    public void changeActor(Actor actor) {
        System.out.println("Change actor");
        selectedActor = actor;
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
            }
            return;
        }
        if (selectedActor instanceof Shifter selectedShifter) {
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

    // TODO to jest do wywalenia ale chcialem pokazac ze updatowanie zaleznosci
    // dziala
    // w szczegolnosci to jest absurdalnie zabugowane i w losowych momentach
    // powstaja losowe bledy, czasem sie nawet graf psuje
    public void demoAnimation() {
        FreePointBuilder freePointBuilder = new FreePointBuilder();
        freePointBuilder.build(plane, transformation, viewPane, 200, 200);
        freePointBuilder.build(plane, transformation, viewPane, 400, 200);
        freePointBuilder.build(plane, transformation, viewPane, 200, 400);
        freePointBuilder.build(plane, transformation, viewPane, 500, 500);
        GeometricPoint a = (GeometricPoint) plane.getClickedShape(200, 200);
        GeometricPoint b = (GeometricPoint) plane.getClickedShape(400, 200);
        GeometricPoint c = (GeometricPoint) plane.getClickedShape(200, 400);
        GeometricPoint d = (GeometricPoint) plane.getClickedShape(500, 500);
        GeometricShapeBuilder builder = new LineThroughPointsBuilder();
        builder.acceptArgument(d);
        builder.acceptArgument(a);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        builder.acceptArgument(d);
        builder.acceptArgument(b);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        builder.acceptArgument(d);
        builder.acceptArgument(c);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        Thread thread = new Thread(new Runnable() {
            double angle = 0;
            double lastTime = System.currentTimeMillis() * 0.001;
            double initTime = System.currentTimeMillis() * 0.001;

            @Override
            public void run() {
                while (true) {
                    double time = System.currentTimeMillis() * 0.001;
                    double deltaTime = time - lastTime;
                    angle += deltaTime;
                    d.x = 500 + 100 * Math.cos(angle);
                    d.y = 500 + 100 * Math.sin(angle);
                    lastTime = time;
                    plane.update();
                    if (time - initTime > 10)
                        break;// koniec po 10 sekundach zeby przetestowac normalne dzialanie programu
                }
            }
        });
        thread.start();
    }
}
