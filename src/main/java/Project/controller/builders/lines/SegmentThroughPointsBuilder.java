package Project.controller.builders.lines;

import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShapeUpdater;
import Project.model.geometricshapes.GeometricPoint;
import Project.model.geometricshapes.GeometricSegment;
import Project.view.viewable.ViewablePlane;

/**
 * A builder class for creating a segment through two points.
 */
public class SegmentThroughPointsBuilder extends LineThroughPointsBuilder {
    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricSegment segment = new GeometricSegment(a, b);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricPoint pA = a;
            private final GeometricPoint pB = b;

            @Override
            public void update() {
                segment.segment.setCoordinates(pA.point, pB.point);
                segment.setCoordinates(segment.segment);
            }
        };
        segment.setUpdater(updater);
        BuilderUtils.addToPlane(segment, viewablePlane);
    }
}
