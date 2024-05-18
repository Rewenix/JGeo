package Project.view;

import Project.controller.Transformation;
import Project.model.BasicCircle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ViewableCircle extends ViewableShape{
    private final Circle drawableShape;
    private final Circle drawableHub;
    private final BasicCircle circle;

    static {
        hub = 4;
    }

    public ViewableCircle(String name, Transformation transformation, BasicCircle circle) {
        super(name, transformation);
        this.circle = circle;
        drawableShape = new Circle();
        drawableShape.setFill(Color.TRANSPARENT);
        drawableShape.setStroke(Color.BLACK);
        drawableHub = new Circle();
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
        drawableShape.setCenterX(transformation.toScreenX(circle.center.x));
        drawableShape.setCenterY(transformation.toScreenY(circle.center.y));
        drawableShape.setRadius(transformation.toScreenX(circle.center.x + circle.radius) - transformation.toScreenX(circle.center.x));
        drawableHub.setCenterX(drawableShape.getCenterX());
        drawableHub.setCenterY(drawableShape.getCenterY());
        drawableHub.setRadius(drawableShape.getRadius());
    }
}
