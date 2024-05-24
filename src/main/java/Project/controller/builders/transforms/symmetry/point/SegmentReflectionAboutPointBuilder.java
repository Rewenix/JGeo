package Project.controller.builders.transforms.symmetry.point;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

public class SegmentReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricSegment segment = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricSegment s) {
            if (segment == null) {
                segment = s;
                return true;
            }
        }
        else if (shape instanceof GeometricPoint p) {
            if (reflectionPoint == null) {
                reflectionPoint = p;
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
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricSegment reflectedSegment = new GeometricSegment("Odbity odcinek");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricSegment pSegment = segment;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setSegment(reflectedSegment, pSegment, pReflectionPoint);
            }
        };
        reflectedSegment.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedSegment, viewablePlane);
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
