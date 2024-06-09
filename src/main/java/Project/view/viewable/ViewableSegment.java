package Project.view.viewable;

import Project.model.basicshapes.BasicSegment;
import Project.model.geometricshapes.GeometricSegment;
import javafx.scene.shape.StrokeLineJoin;

public class ViewableSegment extends ViewableLine {
    private final GeometricSegment geoSegment;

    public ViewableSegment(ViewablePlane viewablePlane, GeometricSegment geoSegment) {
        super(viewablePlane, geoSegment);
        this.geoSegment = geoSegment;
        drawableHub.setStrokeLineJoin(StrokeLineJoin.ROUND);
    }

    @Override
    public void updateDrawable() {
        BasicSegment segment = geoSegment.segment;
        drawableShape.setStartX(getTransformation().toScreenX(segment.p1.x));
        drawableShape.setStartY(getTransformation().toScreenY(segment.p1.y));
        drawableShape.setEndX(getTransformation().toScreenX(segment.p2.x));
        drawableShape.setEndY(getTransformation().toScreenY(segment.p2.y));

        drawableHub.setStartX(drawableShape.getStartX());
        drawableHub.setStartY(drawableShape.getStartY());
        drawableHub.setEndX(drawableShape.getEndX());
        drawableHub.setEndY(drawableShape.getEndY());
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public GeometricSegment getGeometricShape() {
        return geoSegment;
    }
}
