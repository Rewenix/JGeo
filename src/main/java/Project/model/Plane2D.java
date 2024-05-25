package Project.model;

import java.util.ArrayList;

public class Plane2D {
    private final ArrayList<GeometricShape> shapes = new ArrayList<>();

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
        if(shape instanceof GeometricPoint)
            shape.setName(LabelBank.getPointLabel());
        else
            shape.setName(LabelBank.getShapeLabel());
    }

    public GeometricShape removeLastShape() {
        if (!shapes.isEmpty()) {
            GeometricShape shape = shapes.remove(shapes.size() - 1);
            LabelBank.returnLabel(shape.getName());
            return shape;
        }
        return null;
    }

    public void clear() {
        while (!shapes.isEmpty()) {
            removeLastShape();
        }
        LabelBank.reset();
    }

    public void update() {
        for (GeometricShape shape : shapes)
            shape.update();
    }
}
