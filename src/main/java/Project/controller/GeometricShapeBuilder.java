package Project.controller;

import Project.model.GeometricShape;
import Project.view.ViewablePlane;

public interface GeometricShapeBuilder extends Actor {

    boolean acceptArgument(GeometricShape shape);

    boolean isReady();

    void reset();

    void build(ViewablePlane viewablePlane, double planeX, double planeY);

    boolean awaitsPoint();
}