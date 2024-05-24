package Project.model;

import java.util.ArrayList;

public class Plane2D {
    private final ArrayList<GeometricShape> shapes = new ArrayList<>();

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
    }

    public GeometricShape removeLastShape() {
        if (!shapes.isEmpty()) return shapes.remove(shapes.size() - 1);
        return null;
    }

    public void clear() {
        while (!shapes.isEmpty()) {
            removeLastShape();
        }
    }

    public void update() {
        for (GeometricShape shape : shapes)
            shape.update();
    }
}
