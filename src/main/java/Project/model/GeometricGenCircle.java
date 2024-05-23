package Project.model;

public class GeometricGenCircle extends GeometricShape {
    public final GeometricLine line = new GeometricLine();
    public final GeometricCircle circle = new GeometricCircle();

    public GeometricGenCircle(String name) {
        super(name);
    }

    public GeometricGenCircle() {
        super();
    }

    @Override
    public double distance(BasicPoint point) {
        return nowIAm().distance(point);
    }

    public GeometricShape nowIAm() {
        return circle.isDefined() ? circle : line;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return nowIAm().projection(point);
    }

    @Override
    public boolean isDefined() { // chyba nie ma sytuacji, że oba nie są zdefiniowane
        return true;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
