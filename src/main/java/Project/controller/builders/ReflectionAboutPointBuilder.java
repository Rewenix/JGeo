package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

public class ReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private final PointReflectionAboutPointBuilder pointReflectionAboutPointBuilder = new PointReflectionAboutPointBuilder();
    private final SegmentReflectionAboutPointBuilder segmentReflectionAboutPointBuilder = new SegmentReflectionAboutPointBuilder();
    private final LineReflectionAboutPointBuilder lineReflectionAboutPointBuilder = new LineReflectionAboutPointBuilder();
    private final CircleReflectionAboutPointBuilder circleReflectionAboutPointBuilder = new CircleReflectionAboutPointBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        boolean accepted = false;
        if (pointReflectionAboutPointBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (segmentReflectionAboutPointBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (lineReflectionAboutPointBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (circleReflectionAboutPointBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return pointReflectionAboutPointBuilder.isReady() || lineReflectionAboutPointBuilder.isReady()
                || circleReflectionAboutPointBuilder.isReady() || segmentReflectionAboutPointBuilder.isReady();
    }

    @Override
    public void reset() {
        pointReflectionAboutPointBuilder.reset();
        segmentReflectionAboutPointBuilder.reset();
        lineReflectionAboutPointBuilder.reset();
        circleReflectionAboutPointBuilder.reset();
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        if (pointReflectionAboutPointBuilder.isReady()) {
            pointReflectionAboutPointBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (segmentReflectionAboutPointBuilder.isReady()) {
            segmentReflectionAboutPointBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (lineReflectionAboutPointBuilder.isReady()) {
            lineReflectionAboutPointBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (circleReflectionAboutPointBuilder.isReady()) {
            circleReflectionAboutPointBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
    }
}
