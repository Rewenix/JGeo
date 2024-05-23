package Project.controller.builders;

import Project.model.*;
import Project.view.*;

public class BuilderUtils { // Zobaczymy czy jest to przydatne. Najwyzej sie usunie
    public static final double EPSILON = 1e-9; // TODO read it from properties

    public static void addToPlane(GeometricPoint point, ViewablePlane viewablePlane, Plane2D plane) {
        plane.addGeometricShape(point);
        viewablePlane.addViewableShape(new ViewablePoint(point));
    }

    public static void addToPlane(GeometricLine line, ViewablePlane viewablePlane, Plane2D plane) {
        plane.addGeometricShape(line);
        viewablePlane.addViewableShape(new ViewableLine(line));
    }

    public static void addToPlane(GeometricCircle circle, ViewablePlane viewablePlane, Plane2D plane) {
        plane.addGeometricShape(circle);
        viewablePlane.addViewableShape(new ViewableCircle(circle));
    }

    public static void addToPlane(GeometricSegment segment, ViewablePlane viewablePlane, Plane2D plane) {
        plane.addGeometricShape(segment);
        viewablePlane.addViewableShape(new ViewableSegment(segment));
    }

    public static void addToPlane(GeometricGenCircle genCircle, ViewablePlane viewablePlane, Plane2D plane) {
        plane.addGeometricShape(genCircle);
        viewablePlane.addViewableShape(new ViewableGenCircle(genCircle));
    }
}
