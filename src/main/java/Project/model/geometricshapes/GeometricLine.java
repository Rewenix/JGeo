package Project.model.geometricshapes;

import Project.controller.builders.points.PointProjectionOntoLineBuilder;
import Project.model.BasicLine;
import Project.model.BasicPoint;
import Project.model.GeometricShape;

public class GeometricLine extends GeometricShape {
    public final BasicLine line = new BasicLine();

    public GeometricLine(GeometricShape ...parents) {
        super(parents);
    }

    public void setCoordinates(BasicLine line) {
        this.line.setCoordinates(line);
    }

    @Override
    public double distance(BasicPoint point) {
        return line.distance(point);
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return PointProjectionOntoLineBuilder.getPoint(point, line);
    }

    @Override
    public boolean isDefined() {
        return line.isDefined();
    }

    public void makeUndefined() {
        line.setCoordinates(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public static double distance(GeometricPoint p, GeometricLine l) {
        return BasicLine.distance(p.point, l.line);
    }
}
