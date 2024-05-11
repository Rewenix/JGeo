package Project.controller.builders;

import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class SegmentThroughPointsBuilder extends LineThroughPointsBuilder {
    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricSegment segment = new GeometricSegment("Odcinek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                segment.setPoints(pA.x, pA.y, pB.x, pB.y);
            }
        };
        segment.setUpdater(updater);
        segment.update();
        segment.setViewPane(viewPane);
        plane.addGeometricShape(segment);
    }
}
