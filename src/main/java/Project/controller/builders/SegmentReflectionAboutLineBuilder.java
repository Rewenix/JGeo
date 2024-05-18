package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class SegmentReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricSegment segment = null;
    private GeometricLine line = null;

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
        else if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return segment != null && line != null;
    }

    @Override
    public void reset() {
        segment = null;
        line = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricSegment reflectedSegment = new GeometricSegment("Odbity odcinek", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricSegment pSegment = segment;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setSegment(reflectedSegment, pSegment, pLine);
            }
        };
        BuilderUtils.setUpdaterAndAdd(reflectedSegment, updater, viewPane, plane);
    }

    public static void setSegment(GeometricSegment reflectedSegment, BasicSegment pSegment, BasicLine pLine) {
        reflectedSegment.setCoordinates(getSegment(pSegment, pLine));
    }

    public static void setSegment(GeometricSegment reflectedSegment, GeometricSegment pSegment, GeometricLine pLine) {
        setSegment(reflectedSegment, pSegment.segment, pLine.line);
    }

    public static BasicSegment getSegment(BasicSegment pSegment, BasicLine pLine) {
        return new BasicSegment(
                PointReflectionAboutLineBuilder.getPoint(pSegment.p1, pLine),
                PointReflectionAboutLineBuilder.getPoint(pSegment.p2, pLine)
        );
    }
}
