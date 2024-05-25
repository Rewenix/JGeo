package Project.model;

import Project.controller.builders.points.PointProjectionOntoLineBuilder;

public class GeometricLine extends GeometricShape {
    public final BasicLine line = new BasicLine();

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
