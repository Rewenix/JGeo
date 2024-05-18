package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricSegment;
import Project.model.GeometricShapeUpdater;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

/**
 * A builder class for creating a segment through two points.
 */
public class SegmentThroughPointsBuilder extends LineThroughPointsBuilder {
    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricSegment segment = new GeometricSegment("Odcinek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                segment.segment.p1 = pA.point;
                segment.segment.p2 = pB.point;
                segment.segment.setCoordinates(pA.point, pB.point);
                segment.setCoordinates(segment.segment);
            }
        };
        BuilderUtils.setUpdaterAndAdd(segment, updater, viewPane, plane);
    }
}
