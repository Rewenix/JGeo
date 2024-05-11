package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class TangentsFromPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint point;
    private GeometricCircle circle;

    @Override
    public Class<?> expectedClass() {
        return point == null ? GeometricPoint.class : GeometricCircle.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            point = p;
            point.setOnClicked();
            System.out.println("Accepting point");
        } else if (shape instanceof GeometricCircle c) {
            circle = c;
            circle.setOnClicked();
            System.out.println("Accepting circle");
        }
    }

    @Override
    public boolean isReady() {
        return point != null && circle != null;
    }

    @Override
    public void reset() {
        point = null;
        circle = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine tangent1 = new GeometricLine("Tangent1", plane, transformation);
        GeometricLine tangent2 = new GeometricLine("Tangent2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint p = point;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setLines(tangent1, tangent2, p.x, p.y, c.centerX, c.centerY, c.R);
            }
        };
        tangent1.setUpdater(updater);
        tangent2.setUpdater(updater);
        tangent1.update();
        tangent2.update();
        tangent1.setViewPane(viewPane);
        tangent2.setViewPane(viewPane);
        plane.addGeometricShape(tangent1);
        plane.addGeometricShape(tangent2);
    }

    public static void setLines(GeometricLine line1, GeometricLine line2, double x1, double y1, double circleX,
            double circleY, double circleR) {
        double r = circleR;
        double a = circleX;
        double b = circleY; // (x-a)^2 + (y-b)^2 = r^2

        double x1_ = (r * r * (x1 - a)
                + r * (y1 - b) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + a;

        double y1_ = (r * r * (y1 - b)
                - r * (x1 - a) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + b;

        double x2_ = (r * r * (x1 - a)
                - r * (y1 - b) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + a;

        double y2_ = (r * r * (y1 - b)
                + r * (x1 - a) * Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b) - r * r))
                / ((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b)) + b;

        LineThroughPointsBuilder.setLine(line1, x1, y1, x1_, y1_);
        LineThroughPointsBuilder.setLine(line2, x1, y1, x2_, y2_);
    }

    public static void setLines(GeometricLine line1, GeometricLine line2, GeometricPoint point,
            GeometricCircle circle) {
        setLines(line1, line2, point.x, point.y, circle.centerX, circle.centerY, circle.R);
    }
}
