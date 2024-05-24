package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

public class SegmentReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricSegment segment = null;
    private GeometricLine line = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricSegment s) {
            if (segment == null) {
                segment = s;
                return true;
            }
        }
        else if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricSegment ll) {
            if (segment == null) {
                segment = ll;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            if (line == null) {
                line = ll;
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
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricSegment reflectedSegment = new GeometricSegment("Odbity odcinek");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricSegment pSegment = segment;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setSegment(reflectedSegment, pSegment, pLine);
            }
        };
        reflectedSegment.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedSegment, viewablePlane);
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
