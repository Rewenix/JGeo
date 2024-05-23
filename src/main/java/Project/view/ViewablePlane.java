package Project.view;

import Project.controller.Transformation;
import Project.model.GeometricPoint;
import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ViewablePlane { // To jest mocno nie skonczone, ale nie wiem dokladnie co tu ma byc.
    Plane2D plane;
    private final List<ViewableShape> shapes = new ArrayList<>();
    final Transformation transformation = new Transformation();
    Pane viewPane;
    public static double hitbox = 8;

    public ViewablePlane(Plane2D plane) {
        this.plane = plane;
    }

    public ViewablePlane() {
        this(new Plane2D());
    }

    public Plane2D getPlane() {
        return plane;
    }

    /*public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }*/

    public Transformation getTransformation() {
        return transformation;
    }

    public void setViewPane(Pane viewPane) {
        this.viewPane = viewPane;
    }

    public Pane getViewPane() {
        return viewPane;
    }

    public void addViewableShape(ViewableShape shape) {
        shapes.add(shape);
        shape.setViewPane(viewPane);
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
}
