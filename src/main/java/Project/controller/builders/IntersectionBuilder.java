package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.ViewablePlane;

public class IntersectionBuilder implements GeometricShapeBuilder {
    private final LineIntersectionBuilder lineIntersectionBuilder = new LineIntersectionBuilder();
    private final CircleIntersectionBuilder circleIntersectionBuilder = new CircleIntersectionBuilder();
    private final LineAndCircleIntersectionBuilder lineAndCircleIntersectionBuilder = new LineAndCircleIntersectionBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        boolean accepted = false;
        if (lineIntersectionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (circleIntersectionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (lineAndCircleIntersectionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return lineIntersectionBuilder.isReady() || circleIntersectionBuilder.isReady() || lineAndCircleIntersectionBuilder.isReady();
    }

    @Override
    public void reset() {
        lineIntersectionBuilder.reset();
        circleIntersectionBuilder.reset();
        lineAndCircleIntersectionBuilder.reset();
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (lineIntersectionBuilder.isReady()) {
            lineIntersectionBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (circleIntersectionBuilder.isReady()) {
            circleIntersectionBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (lineAndCircleIntersectionBuilder.isReady()) {
            lineAndCircleIntersectionBuilder.build(viewablePlane, planeX, planeY);
        }
    }
}
