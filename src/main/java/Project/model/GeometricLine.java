package Project.model;

import Project.controller.Transformation;
import Project.controller.builders.PointProjectionOntoLineBuilder;
import Project.view.ViewableLine;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class GeometricLine extends GeometricShape {
    public final BasicLine line = new BasicLine();

    @Override
    public int getPriority() {
        return 2;
    }

    public GeometricLine(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        viewableShape = new ViewableLine(name, transformation, line);
    }

    public void setCoordinates(BasicLine line) {
        this.line.setCoordinates(line);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = BasicLine.distance(new BasicPoint(planeX, planeY), line);
        return d / transformation.scale <= plane.hitbox;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return PointProjectionOntoLineBuilder.getPoint(point, line);
    }

    public static double distance(GeometricPoint p, GeometricLine l) {
        return BasicLine.distance(p.point, l.line);
    }
}
