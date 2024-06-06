package Project.controller.builders.intersections;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

import java.util.ArrayList;
import java.util.List;

public class IntersectionBuilder implements GeometricShapeBuilder {
    private final List<GeometricIntersectionBuilder> builders = List.of(new LineIntersectionBuilder(), new CircleIntersectionBuilder(),
            new LineAndCircleIntersectionBuilder(), new GenCircleIntersectionBuilder());

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

    public List<GeometricPoint> getIntersections() {
        for (GeometricIntersectionBuilder builder : builders) {
            if (builder.isReady()) {
                return builder.getIntersections();
            }
        }
        return new ArrayList<>();
    }

    public static List<GeometricPoint> getIntersections(List<GeometricShape> list) {
        IntersectionBuilder builder = new IntersectionBuilder();
        List<GeometricPoint> intersections = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (builder.acceptArgument(list.get(i)) && builder.acceptArgument(list.get(j))) {
                    if (builder.isReady()) {
                        intersections.addAll(builder.getIntersections());
                    }
                }
                builder.reset();
            }
        }
        return intersections;
    }
}
