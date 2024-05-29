package Project.controller.builders.transforms.inversion;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

public class InversionBuilder implements GeometricShapeBuilder {
    private final PointInversionBuilder pointInversionBuilder = new PointInversionBuilder();
    private final LineInversionBuilder lineInversionBuilder = new LineInversionBuilder();
    private final CircleInversionBuilder circleInversionBuilder = new CircleInversionBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        boolean accepted = false;
        if (pointInversionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (lineInversionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (circleInversionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return pointInversionBuilder.isReady() || lineInversionBuilder.isReady() || circleInversionBuilder.isReady();
    }

    @Override
    public void reset() {
        pointInversionBuilder.reset();
        lineInversionBuilder.reset();
        circleInversionBuilder.reset();
    }

    @Override
    public boolean awaitsPoint() {
        return pointInversionBuilder.awaitsPoint() || lineInversionBuilder.awaitsPoint() || circleInversionBuilder.awaitsPoint();
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (pointInversionBuilder.isReady()) {
            pointInversionBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (lineInversionBuilder.isReady()) {
            lineInversionBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (circleInversionBuilder.isReady()) {
            circleInversionBuilder.build(viewablePlane, planeX, planeY);
        }
    }
}
