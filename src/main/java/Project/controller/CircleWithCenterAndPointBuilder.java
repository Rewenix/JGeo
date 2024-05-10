package Project.controller;

import Project.model.*;
import javafx.scene.layout.Pane;

public class CircleWithCenterAndPointBuilder implements GeometricShapeBuilder {
    private GeometricPoint center = null;
    private GeometricPoint point = null;

    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (center == null) {
                center = p;
                System.out.println("Accepting point");
            } else if (p != center) {
                point = p;
                System.out.println("Accepting point");
            }
        }
    }

    @Override
    public boolean isReady() {
        return center != null && point != null;
    }

    @Override
    public void reset() {
        center = null;
        point = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricCircle circle = new GeometricCircle("Okrag", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pCenter = center;
            private GeometricPoint pPoint = point;

            @Override
            public void update() {
                circle.centerX = pCenter.x;
                circle.centerY = pCenter.y;
                circle.R = Math.sqrt((pPoint.x - pCenter.x) * (pPoint.x - pCenter.x)
                        + (pPoint.y - pCenter.y) * (pPoint.y - pCenter.y));
            }
        };
        circle.setUpdater(updater);
        circle.update();
        viewPane.getChildren().add(circle.getDrawableShape());
        plane.addGeometricShape(circle);
    }
}
