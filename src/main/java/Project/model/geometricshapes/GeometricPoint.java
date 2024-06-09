package Project.model.geometricshapes;

import Project.model.GeometricShape;
import Project.model.basicshapes.BasicPoint;

public class GeometricPoint extends GeometricShape {
    public final BasicPoint point = new BasicPoint();
    public boolean isMovable = true;

    public GeometricPoint(double x, double y, GeometricShape... parents) {
        super(parents);
        point.setCoordinates(x, y);
    }

    public GeometricPoint(GeometricShape... parents) {
        this(0, 0, parents);
    }

    public void setCoordinates(BasicPoint point) {
        this.point.setCoordinates(point);
    }

    @Override
    public double distance(BasicPoint point) {
        return this.point.distance(point);
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return this.point;
    }

    @Override
    public boolean isDefined() {
        return point.isDefined();
    }
}
