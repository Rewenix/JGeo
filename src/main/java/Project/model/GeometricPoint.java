package Project.model;

import Project.controller.Transformation;
import Project.view.ViewablePoint;

public class GeometricPoint extends GeometricShape {
    public final BasicPoint point = new BasicPoint();

    @Override
    public int getPriority() {
        return 0;
    }

    public GeometricPoint(String name, Plane2D plane, Transformation transformation, double planeX, double planeY) {
        super(name, plane, transformation);
        point.setCoordinates(planeX, planeY);
        viewableShape = new ViewablePoint(name, transformation, point);
    }

    public GeometricPoint(String name, Plane2D plane, Transformation transformation) {
        this(name, plane, transformation, 0, 0);
    }

    public void setCoordinates(BasicPoint point) {
        this.point.setCoordinates(point);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        return BasicPoint.distance(point, new BasicPoint(planeX, planeY)) / transformation.scale <= plane.hitbox;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return this.point;
    }

    // Utility functions

    public static double distance(GeometricPoint p1, GeometricPoint p2) {
        return BasicPoint.distance(p1.point, p2.point);
    }
}
