package Project.model;

import Project.controller.GeometricShapeBuilder;

import java.util.List;
import java.util.Set;

public abstract class GeometricShape {
    protected String name;
    protected GeometricShapeUpdater updater;
    private final GeometricDefinition definition;

    public GeometricShape() {
        this.definition = new GeometricDefinition(null, null);
    }

    public GeometricShape(Class<? extends GeometricShapeBuilder> builder, List<Set<? extends GeometricShape>> parents) {
        this.definition = new GeometricDefinition(builder, parents);
    }

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void update() {
        updater.update();
    }

    public abstract double distance(BasicPoint point);

    public abstract BasicPoint projection(BasicPoint point);

    public abstract boolean isDefined();

    public boolean hasParentIn(Set<GeometricShape> shapes) {
        return definition.hasParentIn(shapes);
    }

    private static class GeometricDefinition {
        private final Class<? extends GeometricShapeBuilder> builder;
        private final List<Set<? extends GeometricShape>> parents;

        public GeometricDefinition(Class<? extends GeometricShapeBuilder> builder, List<Set<? extends GeometricShape>> parents) {
            this.builder = builder;
            this.parents = parents;
        }

        boolean hasParentIn(Set<GeometricShape> shapes) {
            return parents.stream()
                    .anyMatch(parentSet -> parentSet.stream().anyMatch(shapes::contains));
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GeometricDefinition that = (GeometricDefinition) obj;
            return builder.equals(that.builder) && parents.equals(that.parents);
        }
    }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GeometricShape that = (GeometricShape) obj;
        return definition.equals(that.definition);
    }*/
}
