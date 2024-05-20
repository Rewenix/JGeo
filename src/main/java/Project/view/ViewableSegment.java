package Project.view;

import Project.controller.Transformation;
import Project.model.BasicLine;
import Project.model.BasicSegment;
import javafx.scene.shape.StrokeLineJoin;

public class ViewableSegment extends ViewableLine{
    private final BasicSegment segment;

    public ViewableSegment(String name, Transformation transformation, BasicSegment segment) {
        super(name, transformation, segment);
        this.segment = segment;
        drawableHub.setStrokeLineJoin(StrokeLineJoin.ROUND);
    }

    @Override
    public void updateDrawable() {
        drawableShape.setStartX(transformation.toScreenX(segment.p1.x));
        drawableShape.setStartY(transformation.toScreenY(segment.p1.y));
        drawableShape.setEndX(transformation.toScreenX(segment.p2.x));
        drawableShape.setEndY(transformation.toScreenY(segment.p2.y));

        drawableHub.setStartX(drawableShape.getStartX());
        drawableHub.setStartY(drawableShape.getStartY());
        drawableHub.setEndX(drawableShape.getEndX());
        drawableHub.setEndY(drawableShape.getEndY());
    }
}
