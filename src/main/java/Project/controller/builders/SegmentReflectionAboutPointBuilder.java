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
        BuilderUtils.setUpdaterAndAdd(reflectedSegment, updater, viewPane, plane);
    }

    public static void setSegment(GeometricSegment segment, BasicSegment pSegment, BasicPoint pReflectionPoint) {
        segment.setCoordinates(getSegment(pSegment, pReflectionPoint));
    }

    public static void setSegment(GeometricSegment segment, GeometricSegment pSegment, GeometricPoint pReflectionPoint) {
        setSegment(segment, pSegment.segment, pReflectionPoint.point);
    }

    public static void setSegment(GeometricSegment segment, GeometricPoint pReflectionPoint, GeometricSegment pSegment) {
        setSegment(segment, pSegment, pReflectionPoint);
    }

    public static BasicSegment getSegment(BasicSegment segment, BasicPoint reflectionPoint) {
        BasicPoint p1 = PointReflectionAboutPointBuilder.getPoint(segment.p1, reflectionPoint);
        BasicPoint p2 = PointReflectionAboutPointBuilder.getPoint(segment.p2, reflectionPoint);
        return new BasicSegment(p1, p2);
    }
}
