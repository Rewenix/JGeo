package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

import java.util.ArrayList;
import java.util.List;

public class ReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private final List<GeometricShapeBuilder> builders = List.of(new PointReflectionAboutLineBuilder(),
            new SegmentReflectionAboutLineBuilder(), new LineReflectionAboutLineBuilder(),
            new CircleReflectionAboutLineBuilder());

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        List<Boolean> results = new ArrayList<>();
        builders.forEach(builder -> results.add(builder.acceptArgument(shape)));
        return results.contains(true);
    }

    @Override
    public boolean isReady() {
        return builders.stream().anyMatch(GeometricShapeBuilder::isReady);
    }

    @Override
    public void reset() {
        builders.forEach(GeometricShapeBuilder::reset);
    }

    @Override
    public boolean awaitsPoint() {
        return builders.stream().anyMatch(GeometricShapeBuilder::awaitsPoint);
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        builders.stream().filter(GeometricShapeBuilder::isReady).findFirst()
                .ifPresent(builder -> builder.build(viewablePlane, planeX, planeY));
    }
}
