package Project.model;

import java.util.ArrayList;

public class Plane2D {
    private final ArrayList<GeometricShape> shapes = new ArrayList<>();
    private final LabelBank labelBank = new LabelBank();

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
        labelBank.assignLabel(shape);
    }

    public GeometricShape removeLastShape() {
        if (!shapes.isEmpty()) {
            GeometricShape shape = shapes.remove(shapes.size() - 1);
            labelBank.returnLabel(shape.getName());
            return shape;
        }
        return null;
    }

    public void clear() {
        while (!shapes.isEmpty()) {
            removeLastShape();
        }
        labelBank.reset();
    }

    public void update() {
        for (GeometricShape shape : shapes)
            shape.update();
    }
}