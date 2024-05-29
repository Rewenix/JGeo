package Project.model;

import Project.Config;
import Project.controller.builders.lines.LineThroughPointsBuilder;

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

    public void setCircle(BasicPoint a, BasicPoint b, BasicPoint c) {
        setCoordinates(getCircle(a, b, c));
    }

    public static BasicCircle getCircle(BasicPoint a, BasicPoint b, BasicPoint c) {
        BasicLine ab = LineThroughPointsBuilder.getLine(a, b);
        double dc = ab.distance(c);
        if (dc < Config.EPSILON) {
            return new BasicCircle(new BasicPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
        }

        double x1 = a.x;
        double y1 = a.y;
        double x2 = b.x;
        double y2 = b.y;
        double x3 = c.x;
        double y3 = c.y;
        double A = x1 * (y2 - y3) - y1 * (x2 - x3) + x2 * y3 - x3 * y2;
        double B = (x1 * x1 + y1 * y1) * (y3 - y2) + (x2 * x2 + y2 * y2) * (y1 - y3)
                + (x3 * x3 + y3 * y3) * (y2 - y1);
        double C = (x1 * x1 + y1 * y1) * (x2 - x3) + (x2 * x2 + y2 * y2) * (x3 - x1)
                + (x3 * x3 + y3 * y3) * (x1 - x2);
        double D = (x1 * x1 + y1 * y1) * (x3 * y2 - x2 * y3) + (x2 * x2 + y2 * y2) * (x1 * y3 - x3 * y1)
                + (x3 * x3 + y3 * y3) * (x2 * y1 - x1 * y2);
        double centerX = -B / (2 * A);
        double centerY = -C / (2 * A);
        double R = Math.sqrt(B * B + C * C - 4 * A * D) / (2 * Math.abs(A));
        return new BasicCircle(new BasicPoint(centerX, centerY), R);
    }
}
