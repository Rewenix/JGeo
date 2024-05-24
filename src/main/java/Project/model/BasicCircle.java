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
        this.center.setCoordinates(center);
        this.radius = radius;
    }

    public void setCoordinates(BasicCircle circle) {
        setCoordinates(circle.center, circle.radius);
    }

    public boolean isDefined() {
        return center.isDefined() && Double.isFinite(radius) && radius > 0;
    }
}
