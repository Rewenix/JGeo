package Project.model.geometricshapes;

import Project.model.basicshapes.BasicPoint;
import Project.model.GeometricShape;

public class GeometricGenCircle extends GeometricShape {
    public final GeometricLine line;
    public final GeometricCircle circle;

    public GeometricGenCircle(GeometricShape ...parents) {
        super(parents);
        this.line = new GeometricLine();
        this.circle = new GeometricCircle();
    }

    public GeometricGenCircle(GeometricLine line) {
        this.line = line;
        this.circle = new GeometricCircle();
        circle.makeUndefined();
    }

    public GeometricGenCircle(GeometricCircle circle) {
        this.line = new GeometricLine();
        line.makeUndefined();
        this.circle = circle;
    }

    @Override
    public double distance(BasicPoint point) {
        return nowIAm().distance(point);
    }

    public GeometricShape nowIAm() {
        return circle.isDefined() ? circle : line;
    }

    public GeometricCircle getCircle() {
        return circle;
    }

    public GeometricLine getLine() {
        return line;
    }

    public boolean isLine() {
        return nowIAm() == line;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return nowIAm().projection(point);
    }

    @Override
    public boolean isDefined() { // chyba nie ma sytuacji, że oba nie są zdefiniowane
        return true;
    }
}
