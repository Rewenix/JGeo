package Project.view;

import Project.controller.Transformation;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class ViewableShape {
    public final String name;
    private Pane viewPane;
    protected final Transformation transformation;

    public ViewableShape(String name, Transformation transformation) {
        this.name = name;
        this.transformation = transformation;
    }

    public abstract double getHub();

    public abstract Shape getDrawableShape();

    public abstract Shape getDrawableHub();

    public void setViewPane(Pane viewPane) {
        this.viewPane = viewPane;
        getDrawableHub().setStroke(Color.TRANSPARENT);
        getDrawableHub().setStrokeWidth(getHub());
        viewPane.getChildren().addAll(getDrawableShape(), getDrawableHub());
        getDrawableHub().toBack();
    }

    public void drop() {
        viewPane.getChildren().removeAll(getDrawableShape(), getDrawableHub());
    }

    public void setOnClicked() {
        getDrawableHub().setStroke(Color.CYAN);
    }

    public void unclick() {
        getDrawableHub().setStroke(Color.TRANSPARENT);
    }

    public abstract void updateDrawable();
}