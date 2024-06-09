package Project.controller.builders.intersections;

import Project.controller.GeometricShapeBuilder;
import Project.model.geometricshapes.GeometricPoint;

import java.util.List;

public interface GeometricIntersectionBuilder extends GeometricShapeBuilder {
    List<GeometricPoint> getIntersections();
}
