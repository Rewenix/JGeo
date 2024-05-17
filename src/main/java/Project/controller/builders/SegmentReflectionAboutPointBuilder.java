package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class SegmentReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricSegment segment = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricSegment s) {
            if (segment == null) {
                segment = s;
                segment.setOnClicked();
                System.out.println("Accepting segment");
                return true;
            }
        }
        else if (shape instanceof GeometricPoint p) {
            if (reflectionPoint == null) {
                reflectionPoint = p;
                reflectionPoint.setOnClicked();
                System.out.println("Accepting point");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return segment != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        segment = null;
        reflectionPoint = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricSegment reflectedSegment = new GeometricSegment("Odbity odcinek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricSegment pSegment = segment;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setSegment(reflectedSegment, pSegment, pReflectionPoint);
            }
        };
        reflectedSegment.setUpdater(updater);
        reflectedSegment.update();
        reflectedSegment.setViewPane(viewPane);
        plane.addGeometricShape(reflectedSegment);
    }

    public static void setSegment(GeometricSegment segment, double x1, double y1, double x2, double y2, double reflectionX, double reflectionY) {
        segment.x1 = 2 * reflectionX - x1;
        segment.y1 = 2 * reflectionY - y1;
        segment.x2 = 2 * reflectionX - x2;
        segment.y2 = 2 * reflectionY - y2;
    }

    public static void setSegment(GeometricSegment segment, GeometricSegment pSegment, GeometricPoint pReflectionPoint) {
        setSegment(segment, pSegment.x1, pSegment.y1, pSegment.x2, pSegment.y2, pReflectionPoint.x, pReflectionPoint.y);
    }

    public static void setSegment(GeometricSegment segment, GeometricPoint pReflectionPoint, GeometricSegment pSegment) {
        setSegment(segment, pSegment.x1, pSegment.y1, pSegment.x2, pSegment.y2, pReflectionPoint.x, pReflectionPoint.y);
    }
}
