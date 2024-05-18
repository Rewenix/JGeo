package Project.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Plane2D {
    private ArrayList<GeometricShape> shapes = new ArrayList<>();
    final double hitbox = 8;

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
    }

    public void removeLastShape() {
        if (!shapes.isEmpty()) {
            GeometricShape geometricShape = shapes.remove(shapes.size() - 1);
            Shape shape = geometricShape.getDrawableShape();
            shape.setFill(Color.TRANSPARENT);
            shape.setStroke(Color.TRANSPARENT);
            shape = geometricShape.getDrawableHub();
            shape.setFill(Color.TRANSPARENT);
            shape.setStroke(Color.TRANSPARENT);
        }
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

    public GeometricShape getClickedShape(double planeX, double planeY) {
        for (GeometricShape shape : shapes) {
            if (shape instanceof GeometricPoint point && point.hasPoint(planeX, planeY)) {
                return shape;
            }
        }
        for (GeometricShape shape : shapes) {
            if (shape.hasPoint(planeX, planeY)) {
                return shape;
            }
        }
        return null;
    }

    public List<GeometricShape> getClickedShapesList(double planeX, double planeY) {
        List<GeometricShape> clickedShapes = new ArrayList<>();
        for (GeometricShape shape : shapes)
            if (shape.hasPoint(planeX, planeY))
                clickedShapes.add(shape);

        clickedShapes.sort(GeometricShape.getPriorityComparator());
        return clickedShapes;
    }

    // Na przykład tak można użyć:
    // Predicate<GeometricShape> isNotPoint = shape -> !(shape instanceof GeometricPoint);
    public GeometricShape getClickedShape(double planeX, double planeY, Predicate<GeometricShape> condition) {
        for (GeometricShape shape : shapes) {
            if (condition.test(shape) && shape.hasPoint(planeX, planeY)) {
                return shape;
            }
        }
        return null;
    }

    public void unclickAll() {
        for (GeometricShape shape : shapes) {
            shape.unclick();
        }
    }
}
