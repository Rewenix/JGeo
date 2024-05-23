package Project.model;

import java.util.ArrayList;

public class Plane2D {
    private ArrayList<GeometricShape> shapes = new ArrayList<>();

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
    }

    public void removeLastShape() {
        if (!shapes.isEmpty()) shapes.remove(shapes.size() - 1);
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
