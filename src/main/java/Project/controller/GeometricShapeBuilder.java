package Project.controller;

import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

public interface GeometricShapeBuilder extends Actor {

    boolean acceptArgument(GeometricShape shape);

    boolean isReady();

    void build(ViewablePlane viewablePlane, double planeX, double planeY);

    boolean awaitsPoint();
}