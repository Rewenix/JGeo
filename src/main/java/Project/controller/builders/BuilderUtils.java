package Project.controller.builders;

import Project.model.*;
import Project.view.*;

public class BuilderUtils { // Zobaczymy czy jest to przydatne. Najwyzej sie usunie
    public static final double EPSILON = 1e-9; // TODO read it from properties

    public static void addToPlane(GeometricPoint point, ViewablePlane viewablePlane) {
        viewablePlane.getPlane().addGeometricShape(point);
        viewablePlane.addViewableShape(new ViewablePoint("", viewablePlane, point));
    }

    public static void addToPlane(GeometricLine line, ViewablePlane viewablePlane) {
        viewablePlane.getPlane().addGeometricShape(line);
        viewablePlane.addViewableShape(new ViewableLine("", viewablePlane, line));
    }

    public static void addToPlane(GeometricCircle circle, ViewablePlane viewablePlane) {
        viewablePlane.getPlane().addGeometricShape(circle);
        viewablePlane.addViewableShape(new ViewableCircle("", viewablePlane, circle));
    }

    public static void addToPlane(GeometricSegment segment, ViewablePlane viewablePlane) {
        viewablePlane.getPlane().addGeometricShape(segment);
        viewablePlane.addViewableShape(new ViewableSegment("", viewablePlane, segment));
    }

    public static void addToPlane(GeometricGenCircle genCircle, ViewablePlane viewablePlane) {
        viewablePlane.getPlane().addGeometricShape(genCircle);
        viewablePlane.addViewableShape(new ViewableCircle("", viewablePlane, genCircle.circle));
        viewablePlane.addViewableShape(new ViewableLine("", viewablePlane, genCircle.line));
    }
}
