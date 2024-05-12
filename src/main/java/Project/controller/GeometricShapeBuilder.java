package Project.controller;

import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public interface GeometricShapeBuilder extends Actor {

    boolean acceptArgument(GeometricShape shape);

    boolean isReady();

    void reset();

    void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY);
}