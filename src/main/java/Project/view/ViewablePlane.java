package Project.view;

import Project.controller.Transformation;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ViewablePlane {
    final Plane2D plane;
    private final List<ViewableShape> shapes = new ArrayList<>();
    final Transformation transformation = new Transformation();
    final Pane viewPane;
    static double hitbox = 8; //TODO wczytać z pliku konfiguracyjnego

    public ViewablePlane(Plane2D plane, Pane viewPane) {
        this.plane = plane;
        this.viewPane = viewPane;
    }

    public Plane2D getPlane() {
        return plane;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void addViewableShape(ViewableShape shape) {
        plane.addGeometricShape(shape.getGeometricShape());
        shapes.add(shape);
        shape.setViewPane(viewPane);
        shape.updateDrawable();
    }

    public void updateDrawables() {
        for (ViewableShape shape : shapes)
            shape.updateDrawable();
    }

    public void unclickAll() {
        for (ViewableShape shape : shapes)
            shape.unclick();
    }

    public void removeLastShape() {
        if (!shapes.isEmpty())
            shapes.remove(shapes.size() - 1).drop();
    }

    public void clear() {
        while (!shapes.isEmpty())
            removeLastShape();
    }

    public List<ViewableShape> getClickedShapesList(double screenX, double screenY) {
        List<ViewableShape> clickedShapes = new ArrayList<>();
        for (ViewableShape shape : shapes)
            if (shape.hasPoint(screenX, screenY))
                clickedShapes.add(shape);

        clickedShapes.sort(ViewableShape.getPriorityComparator());
        return clickedShapes;
    }

    public List<ViewableShape> getClickedShapesList(double screenX, double screenY, Predicate<ViewableShape> predicate) {
        List<ViewableShape> clickedShapes = new ArrayList<>();
        for (ViewableShape shape : shapes)
            if (predicate.test(shape) && shape.hasPoint(screenX, screenY))
                clickedShapes.add(shape);

        clickedShapes.sort(ViewableShape.getPriorityComparator());
        return clickedShapes;
    }

    public ViewableShape getClickedShape(double screenX, double screenY) {
        for (ViewableShape shape : shapes) {
            if (shape instanceof ViewablePoint point && point.hasPoint(screenX, screenY)) {
                return shape;
            }
        }
        for (ViewableShape shape : shapes) {
            if (shape.hasPoint(screenX, screenY)) {
                return shape;
            }
        }
        return null;
    }

    public ViewableShape getClickedShape(double screenX, double screenY, Predicate<ViewableShape> predicate) {
        for (ViewableShape shape : shapes) {
            if (predicate.test(shape) && shape.hasPoint(screenX, screenY)) {
                return shape;
            }
        }
        return null;
    }
}
