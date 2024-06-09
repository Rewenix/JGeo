package Project.model.basicshapes;

public class BasicLine {
    public double A, B, C; // Ax + By + C = 0

    public BasicLine(double A, double B, double C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public BasicLine() {
        this(0, 0, 0);
    }

    public void setCoordinates(double A, double B, double C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public void setCoordinates(BasicLine l) {
        setCoordinates(l.A, l.B, l.C);
    }

    public static double distance(BasicPoint p, BasicLine l) {
        return Math.abs(l.A * p.x + l.B * p.y + l.C) / Math.sqrt(l.A * l.A + l.B * l.B);
    }

    public double distance(BasicPoint p) {
        return Math.abs(A * p.x + B * p.y + C) / Math.sqrt(A * A + B * B);
    }

    public boolean isDefined() {
        return Double.isFinite(A) && Double.isFinite(B) && Double.isFinite(C) && (A != 0 || B != 0);
    }
}
