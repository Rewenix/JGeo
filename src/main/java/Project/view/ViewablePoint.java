package Project.view;

import Project.controller.Transformation;
import Project.model.BasicPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewablePoint extends ViewableShape{
    private static final double R = 4;
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final BasicPoint point;

    static {
        hub = 6;
    }

    public ViewablePoint(String name, Transformation transformation, BasicPoint point) {
        super(name, transformation);
        this.point = point;
        drawableShape = new Circle(transformation.toScreenX(point.x), transformation.toScreenY(point.y), R);
        drawableHub = new Circle(transformation.toScreenX(point.x), transformation.toScreenY(point.y), R);
        drawableHub.setFill(Color.TRANSPARENT);
    }

    @Override
    public Shape getDrawableShape() {
        return drawableShape;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public void updateDrawable() {
        drawableShape.setCenterX(transformation.toScreenX(point.x));
        drawableShape.setCenterY(transformation.toScreenY(point.y));
        drawableHub.setCenterX(drawableShape.getCenterX());
        drawableHub.setCenterY(drawableShape.getCenterY());
    }
}