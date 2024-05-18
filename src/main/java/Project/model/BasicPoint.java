package Project.model;

public class BasicPoint {
    public double x, y;

    public BasicPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public BasicPoint() {
        this(0, 0);
    }

    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinates(BasicPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public static double distance(BasicPoint p1, BasicPoint p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public double distance(BasicPoint p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
}
