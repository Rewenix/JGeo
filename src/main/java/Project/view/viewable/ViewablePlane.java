package Project.view.viewable;

import Project.controller.Transformation;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.view.labels.LabelManager;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ViewablePlane {
    final Plane2D plane;
    private final List<ViewableShape> shapes = new ArrayList<>();
    final Transformation transformation = new Transformation();
    final Pane viewPane;
    final LabelManager labelManager;

    public ViewablePlane(Plane2D plane, Pane viewPane) {
        this.plane = plane;
        this.viewPane = viewPane;
        labelManager = new LabelManager(viewPane);
    }

    public Plane2D getPlane() {
        return plane;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void addViewableShape(ViewableShape shape) {
        shapes.add(shape);
        shape.setViewPane(viewPane);
        if (shape instanceof ViewablePoint point)
            labelManager.addPointLabel(point);
    }

    public void updateDrawables() {
        for (ViewableShape shape : shapes)
            shape.updateDrawable();
    }

    public void unclickAll() {
        for (ViewableShape shape : shapes)
            shape.unclick();
    }

    private void dropShape(ViewableShape shape) {
        shape.drop();
        if (shape instanceof ViewablePoint point)
            labelManager.removePointLabel(point);
    }

    public void removeLastShape() {
        if (!shapes.isEmpty()) {
            ViewableShape shape = shapes.remove(shapes.size() - 1);
            dropShape(shape);
        }
    }

    public void clear() {
        while (!shapes.isEmpty())
            removeLastShape();
    }

    public void removeShapes(Set<GeometricShape> shapesSet) {
        shapes.removeIf(viewableShape -> {
            if (shapesSet.contains(viewableShape.getGeometricShape())) {
                dropShape(viewableShape);
                return true;
            }
            return false;
        });
    }

    public List<ViewableShape> getClickedShapesList(double screenX, double screenY) {
        return getClickedShapesList(screenX, screenY, shape -> true);
    }

    public List<ViewableShape> getClickedShapesList(double screenX, double screenY, Predicate<ViewableShape> predicate) {
        return shapes.stream()
                .filter(shape -> shape.hasPoint(screenX, screenY) && predicate.test(shape))
                .sorted(ViewableShape.getPriorityComparator())
                .toList();
    }

    public ViewableShape getClickedShape(double screenX, double screenY) {
        return getClickedShape(screenX, screenY, shape -> true);
    }

    public ViewableShape getClickedShape(double screenX, double screenY, Predicate<ViewableShape> predicate) {
        return shapes.stream()
                .filter(shape -> shape.hasPoint(screenX, screenY) && predicate.test(shape))
                .min(ViewableShape.getPriorityComparator()).orElse(null);
    }
}
