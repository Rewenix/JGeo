package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        if (lineIntersectionBuilder.isReady()) {
            lineIntersectionBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (circleIntersectionBuilder.isReady()) {
            circleIntersectionBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (lineAndCircleIntersectionBuilder.isReady()) {
            lineAndCircleIntersectionBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
    }
}
