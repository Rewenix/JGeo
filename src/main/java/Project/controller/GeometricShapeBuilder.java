package Project.controller;

import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.Group;

public interface GeometricShapeBuilder {
    void acceptArgument(GeometricShape shape);
    boolean isReady();
    void reset();
    void build(Plane2D plane, Transformation transformation, Group viewGroup, double planeX, double planeY);
}