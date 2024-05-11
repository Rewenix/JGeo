package Project.controller.builders;

import Project.controller.Actor;
import Project.controller.Transformation;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public interface GeometricShapeBuilder extends Actor {
    Class<?> expectedClass();

    void acceptArgument(GeometricShape shape);

    boolean isReady();

    void reset();

    void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY);
}