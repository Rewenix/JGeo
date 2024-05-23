package Project.view;

import Project.controller.Transformation;
import Project.model.Plane2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ViewablePlane { // To jest mocno nie skonczone, ale nie wiem dokladnie co tu ma byc.
    public Plane2D plane;
    private List<ViewableShape> shapes = new ArrayList<>();
    public Transformation transformation;
    public Pane viewPane;
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

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

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
    }

    public List<ViewableShape> getClickedShapesList(double screenX, double screenY) {
        List<ViewableShape> clickedShapes = new ArrayList<>();
        double planeX = transformation.toPlaneX(screenX);
        double planeY = transformation.toPlaneY(screenY);
        for (ViewableShape shape : shapes)
            if (shape.hasPoint(planeX, planeY))
                clickedShapes.add(shape);
        return clickedShapes;
    }
}
