package Project.model;

public class BasicCircle {
    public BasicPoint center;
    public double radius;

    public BasicCircle(BasicPoint center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public BasicCircle() {
        this(new BasicPoint(), 0);
    }

    public void setCoordinates(BasicPoint center, double radius) {
        this.center = center;
        this.radius = radius;
    }
}
