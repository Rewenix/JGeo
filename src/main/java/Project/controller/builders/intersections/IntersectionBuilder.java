package Project.controller.builders.intersections;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.ViewablePlane;

public class IntersectionBuilder implements GeometricShapeBuilder {
    private final LineIntersectionBuilder lineIntersectionBuilder = new LineIntersectionBuilder();
    private final CircleIntersectionBuilder circleIntersectionBuilder = new CircleIntersectionBuilder();
    private final LineAndCircleIntersectionBuilder lineAndCircleIntersectionBuilder = new LineAndCircleIntersectionBuilder();
    private final GenCircleIntersectionBuilder genCircleIntersectionBuilder = new GenCircleIntersectionBuilder();

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
        if (genCircleIntersectionBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return lineIntersectionBuilder.isReady() || circleIntersectionBuilder.isReady() || lineAndCircleIntersectionBuilder.isReady() || genCircleIntersectionBuilder.isReady();
    }

    @Override
    public void reset() {
        lineIntersectionBuilder.reset();
        circleIntersectionBuilder.reset();
        lineAndCircleIntersectionBuilder.reset();
        genCircleIntersectionBuilder.reset();
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
        else if (genCircleIntersectionBuilder.isReady()) {
            genCircleIntersectionBuilder.build(viewablePlane, planeX, planeY);
        }
    }
}
