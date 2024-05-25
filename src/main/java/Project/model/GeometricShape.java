package Project.model;

public abstract class GeometricShape {
    protected String name;
    protected GeometricShapeUpdater updater;

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void update() {
        updater.update();
    }

    public abstract double distance(BasicPoint point);

    public abstract BasicPoint projection(BasicPoint point);

    public abstract boolean isDefined();
}
