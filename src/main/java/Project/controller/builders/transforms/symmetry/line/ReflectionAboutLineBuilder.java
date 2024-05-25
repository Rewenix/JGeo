package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.ViewablePlane;

public class ReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private final PointReflectionAboutLineBuilder pointReflectionAboutLineBuilder = new PointReflectionAboutLineBuilder();
    private final SegmentReflectionAboutLineBuilder segmentReflectionAboutLineBuilder = new SegmentReflectionAboutLineBuilder();
    private final LineReflectionAboutLineBuilder lineReflectionAboutLineBuilder = new LineReflectionAboutLineBuilder();
    private final CircleReflectionAboutLineBuilder circleReflectionAboutLineBuilder = new CircleReflectionAboutLineBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        boolean accepted = false;
        if (pointReflectionAboutLineBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (segmentReflectionAboutLineBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (lineReflectionAboutLineBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (circleReflectionAboutLineBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return pointReflectionAboutLineBuilder.isReady() || segmentReflectionAboutLineBuilder.isReady()
                || lineReflectionAboutLineBuilder.isReady() || circleReflectionAboutLineBuilder.isReady();
    }

    @Override
    public void reset() {
        pointReflectionAboutLineBuilder.reset();
        segmentReflectionAboutLineBuilder.reset();
        lineReflectionAboutLineBuilder.reset();
        circleReflectionAboutLineBuilder.reset();
    }

    @Override
    public boolean awaitsPoint() {
        return pointReflectionAboutLineBuilder.awaitsPoint() || segmentReflectionAboutLineBuilder.awaitsPoint()
                || lineReflectionAboutLineBuilder.awaitsPoint() || circleReflectionAboutLineBuilder.awaitsPoint();
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (pointReflectionAboutLineBuilder.isReady()) {
            pointReflectionAboutLineBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (segmentReflectionAboutLineBuilder.isReady()) {
            segmentReflectionAboutLineBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (lineReflectionAboutLineBuilder.isReady()) {
            lineReflectionAboutLineBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (circleReflectionAboutLineBuilder.isReady()) {
            circleReflectionAboutLineBuilder.build(viewablePlane, planeX, planeY);
        }
    }

}
