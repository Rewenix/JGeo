package Project.model;

import Project.model.basicshapes.BasicPoint;

import java.util.List;
import java.util.Set;

public abstract class GeometricShape {
    protected String name;
    protected GeometricShapeUpdater updater;
    private final List<GeometricShape> parents;

    public GeometricShape(GeometricShape... parents) {
        this.parents = List.of(parents);
    }

    public void setUpdater(GeometricShapeUpdater updater) {
        this.updater = updater;
    }

    public void setName(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void update() {
        updater.update();
    }

    public abstract double distance(BasicPoint point);

    public abstract BasicPoint projection(BasicPoint point);

    public abstract boolean isDefined();

    public boolean hasParentIn(Set<GeometricShape> shapes) {
        for (GeometricShape parent : parents) {
            if (shapes.contains(parent)) {
                return true;
            }
        }
        return false;
    }
}
