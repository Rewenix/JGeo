package Project.model;

import java.util.Comparator;

public abstract class GeometricShape {
    protected String name;
    protected GeometricShapeUpdater updater;

    public GeometricShape(String name) {
        this.name = name;
    }

    public GeometricShape() {
        this.name = "";
    }

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void update() {
        updater.update();
    }

    public abstract double distance(BasicPoint point);

    public abstract BasicPoint projection(BasicPoint point);

    public abstract boolean isDefined();

    public abstract int getPriority();

    public static Comparator<GeometricShape> getPriorityComparator() {
        return Comparator.comparingInt(GeometricShape::getPriority);
    }
}
