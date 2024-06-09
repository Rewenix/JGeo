package Project.controller.builders;

import Project.model.geometricshapes.*;
import Project.view.viewable.*;

public class BuilderUtils {

    public static ViewableShape addToPlane(GeometricPoint point, ViewablePlane viewablePlane) {
        point.update();
        viewablePlane.getPlane().addGeometricShape(point);
        ViewableShape viewablePoint = new ViewablePoint(viewablePlane, point);
        viewablePlane.addViewableShape(viewablePoint);
        return viewablePoint;
    }

    public static void addToPlane(GeometricLine line, ViewablePlane viewablePlane) {
        line.update();
        viewablePlane.getPlane().addGeometricShape(line);
        viewablePlane.addViewableShape(new ViewableLine(viewablePlane, line));
    }

    public static void addToPlane(GeometricCircle circle, ViewablePlane viewablePlane) {
        circle.update();
        viewablePlane.getPlane().addGeometricShape(circle);
        viewablePlane.addViewableShape(new ViewableCircle(viewablePlane, circle));
    }

    public static void addToPlane(GeometricSegment segment, ViewablePlane viewablePlane) {
        segment.update();
        viewablePlane.getPlane().addGeometricShape(segment);
        viewablePlane.addViewableShape(new ViewableSegment(viewablePlane, segment));
    }

    public static void addToPlane(GeometricGenCircle genCircle, ViewablePlane viewablePlane) {
        genCircle.update();
        viewablePlane.getPlane().addGeometricShape(genCircle);
        viewablePlane.addViewableShape(new ViewableCircle(viewablePlane, genCircle.circle));
        viewablePlane.addViewableShape(new ViewableLine(viewablePlane, genCircle.line));
    }
}
