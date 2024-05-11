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
            System.out.println("Accepting point");
        } else if (shape instanceof GeometricCircle c) {
            circle = c;
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
        GeometricPoint point1 = new GeometricPoint("TangentPoint", plane, transformation);
        GeometricPoint point2 = new GeometricPoint("TangentPoint", plane, transformation);
        GeometricLine tangent1 = new GeometricLine("Tangent1", plane, transformation);
        GeometricLine tangent2 = new GeometricLine("Tangent2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint p = point;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                double r = c.R;
                double a = c.centerX;
                double b = c.centerY; // (x-a)^2 + (y-b)^2 = r^2

                double x1 = (r * r * (p.x - a)
                        + r * (p.y - b) * Math.sqrt((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b) - r * r))
                        / ((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b)) + a;

                double y1 = (r * r * (p.y - b)
                        - r * (p.x - a) * Math.sqrt((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b) - r * r))
                        / ((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b)) + b;

                double x2 = (r * r * (p.x - a)
                        - r * (p.y - b) * Math.sqrt((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b) - r * r))
                        / ((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b)) + a;

                double y2 = (r * r * (p.y - b)
                        + r * (p.x - a) * Math.sqrt((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b) - r * r))
                        / ((p.x - a) * (p.x - a) + (p.y - b) * (p.y - b)) + b;

                tangent1.setEquation(p.x, p.y, x1, y1);
                tangent2.setEquation(p.x, p.y, x2, y2);
                point1.x = x1;
                point1.y = y1;
                point2.x = x2;
                point2.y = y2;
            }
        };
        point1.setUpdater(updater);
        point2.setUpdater(updater);
        tangent1.setUpdater(updater);
        tangent2.setUpdater(updater);
        point1.update();
        point2.update();
        tangent1.update();
        tangent2.update();
        viewPane.getChildren().add(point1.getDrawableShape());
        viewPane.getChildren().add(point2.getDrawableShape());
        viewPane.getChildren().add(tangent1.getDrawableShape());
        viewPane.getChildren().add(tangent2.getDrawableShape());
        plane.addGeometricShape(point1);
        plane.addGeometricShape(point2);
        plane.addGeometricShape(tangent1);
        plane.addGeometricShape(tangent2);
    }

}
