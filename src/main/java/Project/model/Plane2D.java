package Project.model;

import Project.model.geometricshapes.GeometricGenCircle;
import Project.model.geometricshapes.GeometricPoint;
import Project.model.labels.LabelBank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class Plane2D {
    private final ArrayList<GeometricShape> shapes = new ArrayList<>();
    private final LabelBank labelBank;

    // Putting a default last bank shapes would be good practice
    // The bank will treat it as default anyway, but this way it is explicit
    public Plane2D() {
        LinkedHashMap<Predicate<GeometricShape>, Function<Character, String>> lambdaMap = new LinkedHashMap<>();
        lambdaMap.put(shape -> shape instanceof GeometricPoint, c -> Character.toString(c));
        lambdaMap.put(shape -> true, c -> Character.toString(Character.toLowerCase(c)));
        labelBank = new LabelBank(lambdaMap);
    }

    public void addGeometricShape(GeometricShape shape) {
        shapes.add(shape);
        labelBank.assignLabel(shape);
    }

    public GeometricShape removeLastShape() {
        if (!shapes.isEmpty()) {
            GeometricShape shape = shapes.remove(shapes.size() - 1);
            labelBank.returnLabel(shape);
            return shape;
        }
        return null;
    }

    public Set<GeometricShape> removeShape(GeometricShape shape) {
        Set<GeometricShape> dropped = new HashSet<>();
        for (GeometricShape currentShape : shapes)
            if (currentShape.equals(shape) || currentShape.hasParentIn(dropped)) {
                dropped.add(currentShape);
                if (currentShape instanceof GeometricGenCircle genCircle) {
                    dropped.add(genCircle.getLine());
                    dropped.add(genCircle.getCircle());
                }
                labelBank.returnLabel(currentShape);
            }
        shapes.removeAll(dropped);
        return dropped;
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