package Project.model;

public class GeometricPoint extends GeometricShape {
    public final BasicPoint point = new BasicPoint();

    public GeometricPoint(String name, double x, double y) {
        super(name);
        point.setCoordinates(x, y);
    }

    public GeometricPoint(double x, double y) {
        this("", x, y);
    }

    public GeometricPoint(String name) {
        this(name, 0, 0);
    }

    public GeometricPoint() {
        super();
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
