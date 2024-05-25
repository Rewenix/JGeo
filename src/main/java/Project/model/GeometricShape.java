package Project.model;

public abstract class GeometricShape {
    protected String name;
    protected GeometricShapeUpdater updater;

    public GeometricShape(String name) {
        this.name = name;
        //this.updater = () -> {};
    }

    public GeometricShape() {
        this("");
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
}
