package Project.controller.builders;

import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public class BuilderUtils { // Zobaczymy czy jest to przydatne. Najwyzej sie usunie
    public static final double EPSILON = 1e-9;

    public static void setUpdaterAndAdd(GeometricShape shape, GeometricShapeUpdater updater, Pane viewPane, Plane2D plane) {
        shape.setUpdater(updater);
        shape.update();
        shape.setViewPane(viewPane);
        plane.addGeometricShape(shape);
    }
}
